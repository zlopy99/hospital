package Admin;

import DataBase.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class AdminController implements Initializable{

    @FXML
    private TableView<ListOfDoktors> DoktorTable;

    @FXML
    private TableColumn<?, ?> logDoktor;

    @FXML
    private TableColumn<?, ?> lozinkaDok;

    @FXML
    private TableColumn<?, ?> imeDok;

    @FXML
    private TableColumn<?, ?> odjelDok;

    @FXML
    private TableColumn<?, ?> opisDok;

    @FXML
    private Label LogiraniAdmin;

    @FXML
    private Button LogoutBtn;

    @FXML
    private Label Adminname;

    @FXML
    private TextField TrazeniDoktor;

    private ObservableList<ListOfDoktors> doktorList;

    Connection con = null;
    ResultSet rs = null;
    PreparedStatement ps = null;
    String TDoktor = null;

    String query = "SELECT korisnik.Korisnicko_ime, korisnik.Lozinka, liječnik.Ime_Prezime, liječnik.Opis, odjel.Naziv " +
            "FROM korisnik join liječnik " +
            "ON korisnik.korisnik_id = liječnik.korisnik_id join odjel " +
            "ON liječnik.odjel_id = odjel.odjel_id";

    // Metoda koja se odmah pokrene
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {

            con = DbConnection.getConnection();
            doktorList = FXCollections.observableArrayList();

            ps = con.prepareStatement(query);
            rs = ps.executeQuery();

            while (rs.next()){
                doktorList.add(new ListOfDoktors(rs.getString(1),
                        rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5)));
            }

        }catch (Exception e){
            System.out.println("Error je: " + e);
        }
        logDoktor.setCellValueFactory(new PropertyValueFactory<>("User_Name"));
        lozinkaDok.setCellValueFactory(new PropertyValueFactory<>("Password"));
        imeDok.setCellValueFactory(new PropertyValueFactory<>("Ime_Prezime"));
        opisDok.setCellValueFactory(new PropertyValueFactory<>("Opis"));
        odjelDok.setCellValueFactory(new PropertyValueFactory<>("Odjel"));

        DoktorTable.setItems(null);
        DoktorTable.setItems(doktorList);
    }

    // Ime administratora
    public void ImeAdmina(int parametar){
        try {
            PreparedStatement prep = con.prepareStatement("SELECT Ime_Prezime FROM administrator WHERE id_korisnika = ?");
            prep.setInt(1,parametar);
            ResultSet rs = prep.executeQuery();
            while (rs.next()){
                LogiraniAdmin.setText(rs.getString(1));
            }
            con.close();

        }catch (Exception e){
            e.getMessage();
        }
    }

    // Osvježavanje tablice svih doktora
    @FXML
    private void loadAllDoktors(javafx.scene.input.MouseEvent mouseEvent){
        try {
            doktorList.clear();
            Connection con = DbConnection.getConnection();
            doktorList = FXCollections.observableArrayList();

            ResultSet rs = con.createStatement().executeQuery("SELECT korisnik.Korisnicko_ime, korisnik.Lozinka, liječnik.Ime_Prezime, liječnik.Opis, odjel.Naziv " +
                    "FROM liječnik join korisnik " +
                    "ON liječnik.korisnik_id = korisnik.korisnik_id join odjel " +
                    "ON liječnik.odjel_id = odjel.odjel_id");
            while (rs.next()){
                doktorList.add(new ListOfDoktors(rs.getString(1),
                        rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5)));
                DoktorTable.setItems(doktorList);
            }
            con.close();
        }catch (Exception e){
            e.getMessage();
        }
    }

    // Tražilica doktora
    @FXML
    private void TraziD(javafx.scene.input.MouseEvent mouseEvent){
        try {
            if(TrazeniDoktor.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Upišite nešto u tražilicu.");
                Optional<ButtonType>  result = alert.showAndWait();
            }
            else {
                doktorList.clear();
                con = DbConnection.getConnection();
                doktorList = FXCollections.observableArrayList();
                TDoktor = TrazeniDoktor.getText() + "%";
                ps = con.prepareStatement("SELECT korisnik.Korisnicko_ime, korisnik.Lozinka, liječnik.Ime_Prezime, liječnik.Opis, odjel.Naziv " +
                        "FROM liječnik join korisnik " +
                        "ON liječnik.korisnik_id = korisnik.korisnik_id join odjel " +
                        "ON liječnik.odjel_id = odjel.odjel_id WHERE liječnik.Ime_Prezime LIKE ?");
                ps.setString(1,TDoktor);
                rs = ps.executeQuery();
                while (rs.next()){
                    doktorList.add(new ListOfDoktors(rs.getString(1),
                            rs.getString(2), rs.getString(3),
                            rs.getString(4), rs.getString(5)));
                    DoktorTable.setItems(doktorList);
                }

                rs.close();
                ps.close();
                con.close();
            }

        }catch (Exception e){
            e.getMessage();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Upišite nešto u tražilicu.");
            Optional<ButtonType>  result = alert.showAndWait();

            refresh();
        }
    }

    // Osvježi tabelu kada se doktor izbriše
    @FXML
    private void refresh(){
        try {
            doktorList.clear();
            Connection con = DbConnection.getConnection();
            doktorList = FXCollections.observableArrayList();
            ResultSet rs = con.createStatement().executeQuery("SELECT korisnik.Korisnicko_ime, korisnik.Lozinka, liječnik.Ime_Prezime, liječnik.Opis, odjel.Naziv " +
                    "FROM liječnik join korisnik " +
                    "ON liječnik.korisnik_id = korisnik.korisnik_id join odjel " +
                    "ON liječnik.odjel_id = odjel.odjel_id");
            while (rs.next()){
                doktorList.add(new ListOfDoktors(rs.getString(1),
                        rs.getString(2), rs.getString(3),
                        rs.getString(4), rs.getString(5)));
                DoktorTable.setItems(doktorList);
            }

            con.close();

        }catch (Exception e){
            e.getMessage();
        }
    }

    // Dodavanje Doktora
    @FXML
    private void addDoktor(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getResource("/Admin/addDoktor.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Doktor Add");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
        refresh();
    }

    // Uređivanje Doktora
    @FXML
    private void editDoktor(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Admin/editDoktor.fxml"));
        Parent parent = (Parent) loader.load();
        Scene scene = new Scene(parent);
        Stage stage = new Stage();
        stage.setTitle("Doktor Edit");
        stage.setScene(scene);
        stage.setResizable(false);
        try {
            con = DbConnection.getConnection();
            ObservableList<ListOfDoktors> Izabrani;
            Izabrani = DoktorTable.getSelectionModel().getSelectedItems();
            String pom1 = Izabrani.iterator().next().getUser_Name();
            String pom2 = Izabrani.iterator().next().getPassword2();
            String pom3 = Izabrani.iterator().next().getIme_Prezime();
            String pom4 = Izabrani.iterator().next().getOdjel();
            String pom5 = Izabrani.iterator().next().getOpis();
            if(!Izabrani.isEmpty()){
                stage.initModality(Modality.APPLICATION_MODAL);

                EditDoktorController editControler = loader.getController();
                editControler.getSelection(pom1, pom2, pom3, pom4, pom5);

                stage.show();
                refresh();
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Izaberite Doktora.");
                alert.showAndWait();
            }
            con.close();

        }catch (Exception e){
            e.getMessage();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Izaberite Doktora.");
            alert.showAndWait();
        }
    }

    // Brisanje Doktora
    @FXML
    private void dellDoktor(javafx.scene.input.MouseEvent mouseEvent){
        try {
            con = DbConnection.getConnection();
            ArrayList<String> Lista = new ArrayList<String>();
            ResultSet rs = con.createStatement().executeQuery("SELECT Korisnicko_ime FROM korisnik");
            while (rs.next()){
                Lista.add(rs.getString(1));
            }

            ObservableList<ListOfDoktors> Izabrani;
            Izabrani = DoktorTable.getSelectionModel().getSelectedItems();
            String pomIzabrani = Izabrani.iterator().next().getUser_Name();

            PreparedStatement pomS = con.prepareStatement("SELECT korisnik_id FROM korisnik WHERE Korisnicko_ime = ?");
            pomS.setString(1,pomIzabrani);
            ResultSet RSpom = pomS.executeQuery();
            int IDkorisnik = 0;
            while (RSpom.next()){
                IDkorisnik = RSpom.getInt(1);
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Želite li ukloniti doktora?");
            Optional<ButtonType>  result = alert.showAndWait();
            //System.out.println(result.get().getButtonData());

            if(Lista.contains(pomIzabrani) && (result.get().getButtonData().toString().equalsIgnoreCase("OK_DONE"))){

                PreparedStatement prep = con.prepareStatement("SELECT odjel_id FROM liječnik WHERE korisnik_id = ?");
                prep.setInt(1,IDkorisnik);
                ResultSet pomRS = prep.executeQuery();
                int IDodjel = 0;
                while (pomRS.next()){
                    IDodjel = pomRS.getInt(1);
                }

                PreparedStatement prep2 = con.prepareStatement("SELECT lijecnik_id FROM liječnik WHERE korisnik_id = ?");
                prep2.setInt(1,IDkorisnik);
                ResultSet pomRS2 = prep2.executeQuery();
                int IDdoktor = 0;
                while (pomRS2.next()){
                    IDdoktor = pomRS2.getInt(1);
                }
                PreparedStatement prep3 = con.prepareStatement("delete from pacijent where pacijent.pacijent_id in (" +
                        "    select id_pacijenta from pregled where pregled.id_lijecnika = ?" +
                        "    )");
                prep3.setInt(1,IDdoktor);
                prep3.executeUpdate();


                String query = "DELETE FROM korisnik WHERE Korisnicko_ime LIKE ?";
                PreparedStatement stm = null;
                stm = con.prepareStatement(query);
                stm.setString(1, pomIzabrani);
                stm.executeUpdate();

                String query2 = "DELETE FROM odjel WHERE odjel_id=?";
                PreparedStatement stm2 = null;
                stm2 = con.prepareStatement(query2);
                stm2.setInt(1, IDodjel);
                stm2.executeUpdate();

                alert.close();
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Uspješno ste uklonili doktora.");
                alert.showAndWait();

                refresh();
            }
            con.close();

        }catch (Exception e){
            e.getMessage();
            System.out.println(e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Izaberite koga želite ukloniti.");
            alert.showAndWait();
        }
    }

    // Logout
    @FXML
    private void Logout(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage)LogoutBtn.getScene().getWindow();
        stage.close();
        Parent parent = FXMLLoader.load(getClass().getResource("/Login/login.fxml"));
        Scene scene = new Scene(parent);
        stage.setTitle("Medical System");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
}
