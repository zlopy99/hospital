package Doktor;

import Admin.ListOfDoktors;
import DataBase.DbConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Optional;

public class DoktorController {

    @FXML
    private Text DoktorName;

    @FXML
    private Text ImePrezime;

    @FXML
    private Text JMBG;

    @FXML
    private Text Odjel;

    @FXML
    private Text Opis;

    @FXML
    private Text Lozinka;

    @FXML
    private Button DokLogout;

    @FXML
    private TextField editNovoIme;

    @FXML
    private TextField editNoviJMBG;

    @FXML
    private TextField editNoviodjel;

    @FXML
    private TextField editNoviOpis;

    @FXML
    private TextField editNovaLozinka;

    @FXML
    private Text DokName;

    @FXML
    private TableView<ListOfPacjents> PacijentTable;

    @FXML
    private TableColumn<?, ?> LijecnikIDP;

    @FXML
    private TableColumn<?, ?> DatumP;

    @FXML
    private TableColumn<?, ?> PregledP;

    @FXML
    private TableColumn<?, ?> PacijentP;

    @FXML
    private TableColumn<?, ?> JMBGP;

    @FXML
    private TableColumn<?, ?> OsiguranjeP;

    @FXML
    private TableColumn<?, ?> CovidP;

    @FXML
    private Button DokSave;

    @FXML
    private Button DokReturn;

    Connection con = null;
    PreparedStatement prepS = null;
    ResultSet rs = null;
    int DokKorisnikID = 0;
    int DoktorID = 0;
    int OdjelID = 0;
    String lozinka = null;
    private ObservableList<ListOfPacjents> pacijentiLista;

    // Lista pacijenata
    private void ispisPacijenata(){
        try {
            con = DbConnection.getConnection();
            pacijentiLista = FXCollections.observableArrayList();
            prepS = con.prepareStatement("SELECT liječnik.lijecnik_id, pregled.Datum, pregled.Opis, pacijent.Ime_Prezime, pacijent.JMBG, pacijent.Zdravstveno_osiguranje, pacijent.Covid_cjepivo " +
                    "FROM liječnik JOIN pregled ON liječnik.lijecnik_id = pregled.id_lijecnika JOIN pacijent " +
                    "ON pacijent.pacijent_id = pregled.id_pacijenta WHERE liječnik.lijecnik_id = ?");
            prepS.setInt(1,DoktorID);
            rs = prepS.executeQuery();
            while (rs.next()){
                pacijentiLista.add(new ListOfPacjents(rs.getInt(1),rs.getString(2),rs.getString(3), rs.getString(4),
                        rs.getString(5),rs.getString(6),rs.getString(7)));
            }
            LijecnikIDP.setCellValueFactory(new PropertyValueFactory<>("DoktorID"));
            DatumP.setCellValueFactory(new PropertyValueFactory<>("Datum"));
            PregledP.setCellValueFactory(new PropertyValueFactory<>("Pregled"));
            PacijentP.setCellValueFactory(new PropertyValueFactory<>("Pacijent"));
            JMBGP.setCellValueFactory(new PropertyValueFactory<>("JMBG"));
            OsiguranjeP.setCellValueFactory(new PropertyValueFactory<>("Osiguranje"));
            CovidP.setCellValueFactory(new PropertyValueFactory<>("Cjepivo"));

            PacijentTable.setItems(null);
            PacijentTable.setItems(pacijentiLista);

            prepS.close();
            rs.close();
            con.close();

        }catch (Exception e){
            e.getMessage();
            System.out.println(e);
        }
    }

    // Osviježi listu pacijenata
    @FXML
    private void OsvježiListu(javafx.scene.input.MouseEvent mouseEvent){
        try {
            con = DbConnection.getConnection();
            pacijentiLista = FXCollections.observableArrayList();
            prepS = con.prepareStatement("SELECT liječnik.lijecnik_id, pregled.Datum, pregled.Opis, pacijent.Ime_Prezime, pacijent.JMBG, pacijent.Zdravstveno_osiguranje, pacijent.Covid_cjepivo " +
                    "FROM liječnik JOIN pregled ON liječnik.lijecnik_id = pregled.id_lijecnika JOIN pacijent " +
                    "ON pacijent.pacijent_id = pregled.id_pacijenta WHERE liječnik.lijecnik_id = ?");
            prepS.setInt(1,DoktorID);
            rs = prepS.executeQuery();
            while (rs.next()){
                pacijentiLista.add(new ListOfPacjents(rs.getInt(1),rs.getString(2),rs.getString(3),
                        rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7)));
            }

            PacijentTable.setItems(null);
            PacijentTable.setItems(pacijentiLista);

            prepS.close();
            rs.close();
            con.close();

        }catch (Exception e){
            e.getMessage();
            System.out.println(e);
        }
    }

    // Refresh
    @FXML
    private void refreshP(){
        try {
            pacijentiLista.clear();
            con = DbConnection.getConnection();
            pacijentiLista = FXCollections.observableArrayList();
            prepS = con.prepareStatement("SELECT liječnik.lijecnik_id, pregled.Datum, pregled.Opis, pacijent.Ime_Prezime, pacijent.JMBG, pacijent.Zdravstveno_osiguranje, pacijent.Covid_cjepivo " +
                    "FROM liječnik JOIN pregled ON liječnik.lijecnik_id = pregled.id_lijecnika JOIN pacijent " +
                    "ON pacijent.pacijent_id = pregled.id_pacijenta WHERE liječnik.lijecnik_id = ?");
            prepS.setInt(1,DoktorID);
            rs = prepS.executeQuery();
            while (rs.next()){
                pacijentiLista.add(new ListOfPacjents(rs.getInt(1),rs.getString(2),rs.getString(3),
                        rs.getString(4),rs.getString(5),rs.getString(6),rs.getString(7)));
            }

            PacijentTable.setItems(null);
            PacijentTable.setItems(pacijentiLista);

            prepS.close();
            rs.close();
            con.close();

        }catch (Exception e){
            e.getMessage();
            System.out.println(e);
        }
    }

    // Dodavanje Pacijenta
    @FXML
    private void addPacijenta(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        //Parent parent = FXMLLoader.load(getClass().getResource("/Doktor/addPacijent.fxml"));

        FXMLLoader pacijentLoader = new FXMLLoader(getClass().getResource("/Doktor/addPacijent.fxml"));
        Parent pacijentRoot = (Parent) pacijentLoader.load();
        AddPacijentController pacijentController = pacijentLoader.getController();
        pacijentController.getID(DoktorID);

        Scene scene = new Scene(pacijentRoot);
        Stage stage = new Stage();
        stage.setTitle("Pacijent Add");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.show();
    }

    // Brisanje pacijenata
    @FXML
    public void BrisanjePacijenata(javafx.scene.input.MouseEvent mouseEvent){
        try {
            ObservableList<ListOfPacjents> Izabrani;
            Izabrani = PacijentTable.getSelectionModel().getSelectedItems();
            String pomIzabrani = Izabrani.iterator().next().getJMBG();

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText(null);
            alert.setContentText("Želite li ukloniti pacijenta?");
            Optional<ButtonType>  result = alert.showAndWait();
            //System.out.println(result.get().getButtonData());
            System.out.println(pomIzabrani);

            if((result.get().getButtonData().toString().equalsIgnoreCase("OK_DONE"))){

                String query = "DELETE FROM pacijent WHERE JMBG LIKE ?";
                con = DbConnection.getConnection();
                prepS = con.prepareStatement(query);
                prepS.setString(1, pomIzabrani);
                prepS.executeUpdate();

                alert.close();
                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setHeaderText(null);
                alert.setContentText("Uspješno ste uklonili pacijenta.");
                alert.showAndWait();

                prepS.close();
                con.close();

                refreshP();
            }

        }catch (Exception e){
            e.getMessage();
            System.out.println(e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Izaberite koga želite ukloniti.");
            alert.showAndWait();
        }
    }

    // Edit pacijenta

    // Set Stuf up to eddit
    private void setStuffUp() {
        editNovoIme.setText(ImePrezime.getText());
        editNoviJMBG.setText(JMBG.getText());
        editNoviodjel.setText(Odjel.getText());
        editNoviOpis.setText(Opis.getText());
        editNovaLozinka.setText(null);
        DokName.setText(ImePrezime.getText());
    }

    // Doktor korisnicko ime
    public void ImeDoktora(int DokID) {
        DokKorisnikID = DokID;
        try {
            con = DbConnection.getConnection();
            prepS = con.prepareStatement("SELECT lijecnik_id, Ime_Prezime, JMBG, Opis, odjel_id FROM liječnik WHERE korisnik_id = ?");
            prepS.setInt(1,DokID);
            rs = prepS.executeQuery();
            while (rs.next()){
                DoktorID = rs.getInt(1);
                ImePrezime.setText(rs.getString(2));
                JMBG.setText(rs.getString(3));
                Opis.setText(rs.getString(4));
                OdjelID = rs.getInt(5);
            }
            prepS.close();
            rs.close();

            prepS = con.prepareStatement("SELECT Korisnicko_ime, Lozinka FROM korisnik WHERE korisnik_id = ?");
            prepS.setInt(1,DokID);
            rs = prepS.executeQuery();
            while (rs.next()){
                DoktorName.setText(rs.getString(1));
                lozinka = rs.getString(2);
                Lozinka.setText("ENCRIPTED");
            }
            prepS.close();
            rs.close();

            prepS = con.prepareStatement("SELECT Naziv FROM odjel WHERE odjel_id = ?");
            prepS.setInt(1,OdjelID);
            rs = prepS.executeQuery();
            while (rs.next()){
                Odjel.setText(rs.getString(1));
            }
            prepS.close();
            rs.close();

            ispisPacijenata();
            setStuffUp();

            con.close();

        }catch (Exception e){
            e.getMessage();
        }
    }

    // Logout
    @FXML
    private void Logout(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        Stage stage = (Stage)DokLogout.getScene().getWindow();
        stage.close();
        Parent parent = FXMLLoader.load(getClass().getResource("/Login/login.fxml"));
        Scene scene = new Scene(parent);
        stage.setTitle("Medical System");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    // Return everything
    public void returnAll(ActionEvent ae) {
        setStuffUp();
    }

    //Odmah promjeni
    private void promijeni(){
        ImePrezime.setText(editNovoIme.getText());
        JMBG.setText(editNoviJMBG.getText());
        Odjel.setText(editNoviodjel.getText());
        Opis.setText(editNoviOpis.getText());
    }

    // Save changes
    public void SaveChanges(ActionEvent ae){
        try{
            if(editNovoIme.getText().isEmpty() || editNovaLozinka.getText().isEmpty() || editNoviodjel.getText().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Polja:Ime Prezime, Lozinka i Odjel moraju biti popunjeni.");
                alert.showAndWait();
            }else {
                if (editNovaLozinka.getText().length() >= 6){
                    Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                    alert1.setHeaderText(null);
                    alert1.setContentText("Želite li promijeniti podatke?");
                    Optional<ButtonType> result = alert1.showAndWait();

                    if (result.get().getButtonData().toString().equalsIgnoreCase("OK_DONE")){
                        con = DbConnection.getConnection();
                        prepS = con.prepareStatement("UPDATE `liječnik` SET " +
                                "`Ime_Prezime`=?,`JMBG`=?," +
                                "`Opis`=? WHERE korisnik_id = ? AND odjel_id = ?");
                        prepS.setString(1,editNovoIme.getText());
                        prepS.setString(2,editNoviJMBG.getText());
                        prepS.setString(3,editNoviOpis.getText());
                        prepS.setInt(4,DokKorisnikID);
                        prepS.setInt(5,OdjelID);
                        prepS.execute();
                        prepS.close();

                        prepS = con.prepareStatement("UPDATE korisnik SET "+
                                "Lozinka = PASSWORD(?) WHERE korisnik_id = ?");
                        prepS.setString(1,editNovaLozinka.getText());
                        prepS.setInt(2,DokKorisnikID);
                        prepS.execute();
                        prepS.close();

                        prepS = con.prepareStatement("UPDATE odjel SET "+
                                "Naziv = ? WHERE odjel_id = ?");
                        prepS.setString(1,editNoviodjel.getText());
                        prepS.setInt(2,OdjelID);
                        prepS.execute();
                        prepS.close();

                        promijeni();

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setContentText("Uspiješno ste promijenili podatke.");
                        alert.showAndWait();
                    }
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Lozinka mora biti minimum 6 znakova.");
                    alert.showAndWait();
                }
            }

        }catch (Exception e){
            e.getMessage();
            System.out.println(e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Lozinka mora biti minimum 6 znakova.\n" +
                    "(Upišite vašu lozinku ako želite promijeniti nešto)");
            alert.showAndWait();
        }
    }
}
