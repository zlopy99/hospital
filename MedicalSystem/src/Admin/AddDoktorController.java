package Admin;

import DataBase.DbConnection;
import Login.Option;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddDoktorController implements Initializable {
    @FXML
    private TextField addKorisnikIme;

    @FXML
    private TextField addLozinka;

    @FXML
    private ComboBox<Option> addUloga;

    @FXML
    private TextField addOdjel;

    @FXML
    private TextField addOpis;

    @FXML
    private TextField addImePrezime;

    Connection con = null;
    ResultSet rs = null;
    PreparedStatement ps = null;
    PreparedStatement ps2 = null;
    PreparedStatement ps3 = null;
    String query = null;
    String query2 = null;
    String query3 = null;
    int KorisnikID;
    int OdjelID;

    public void initialize(URL url, ResourceBundle resourceBundle) {

        addUloga.setItems(FXCollections.observableArrayList(Option.values()));
        addUloga.setValue(Option.valueOf("Lijecnik"));
    }


    @FXML
    void clean(MouseEvent event) {
        addKorisnikIme.setText(null);
        addLozinka.setText(null);
        //addUloga.setValue(null);
        addImePrezime.setText(null);
        addOpis.setText(null);
        addOdjel.setText(null);
    }
    @FXML
    void save(MouseEvent event) {
        try{
            String korisnikIme = addKorisnikIme.getText();
            String korisnikLozinka = addLozinka.getText();

            con = DbConnection.getConnection();
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("SELECT Korisnicko_ime FROM korisnik");
            ArrayList<String> Lista = new ArrayList<String>();
            while (rs.next()){
                Lista.add(rs.getString(1));
            }

            if(!(addKorisnikIme.getText().isEmpty() || addLozinka.getText().isEmpty() || addImePrezime.getText().isEmpty() ||
            addUloga.getValue().toString().isEmpty() || addOdjel.getText().isEmpty())){
                if(!Lista.contains(korisnikIme)){
                    if(korisnikLozinka.length() >= 6){
                        getQuery();
                        insert();
                        secondSafe();
                        thirdSafe();

                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setContentText("Korisnik je upisan u bazu.");
                        alert.showAndWait();
                    }
                    else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText(null);
                        alert.setContentText("Lozinka mora biti minimum 6 znakova.");
                        alert.showAndWait();
                    }
                }
                else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Korisnik već postoji.");
                    alert.showAndWait();
                }
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Molimo vas popunite sva polja.(opis je opcionalan)");
                alert.showAndWait();
            }

            con.close();

        }catch (Exception e){
            e.getMessage();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Molimo vas popunite sva polja.(opis je opcionalan)");
            alert.showAndWait();
        }
    }


    // Unos korisnika
    private void getQuery() {
        query = "INSERT INTO `korisnik`(`Uloga`, `Korisnicko_ime`, `Lozinka`) VALUES (?,?,?)";
    }
    private void insert() {
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, addUloga.getValue().toString());
            ps.setString(2, addKorisnikIme.getText());
            ps.setString(3, addLozinka.getText());
            ps.execute();

            con.close();

        }catch (Exception e){
            e.getMessage();
        }
    }


    // Unos odjela
    private void getQuery2() {
        query2 = "INSERT INTO `odjel`(`Naziv`) VALUES (?)";
    }
    private void insert2() {
        try {
            ps2 = con.prepareStatement(query2);
            ps2.setString(1, addOdjel.getText());
            ps2.execute();

            con.close();

        }catch (Exception e){
            e.getMessage();
        }
    }
    private void secondSafe(){
        try {
            con = DbConnection.getConnection();

            getQuery2();
            insert2();

            con.close();

        }catch (Exception e){
            e.getMessage();
        }
    }


    // Unos liječnika
    private void getQuery3(){
        query3 = "INSERT INTO `liječnik`(`Ime_Prezime`, `Opis`, `korisnik_id`, `odjel_id`) VALUES (?,?,?,?)";
    }
    private void insert3(){
        try {
            PreparedStatement pomPrep = con.prepareStatement("SELECT korisnik_id FROM korisnik WHERE Korisnicko_ime=?");
            pomPrep.setString(1,addKorisnikIme.getText());
            //pomPrep.execute();
            rs = pomPrep.executeQuery();
            while (rs.next()){
                KorisnikID = rs.getInt(1);
            }
            System.out.println(KorisnikID);

            PreparedStatement pomPrep2 = con.prepareStatement("SELECT odjel_id FROM odjel");
            //pomPrep.execute();
            rs = pomPrep2.executeQuery();
            while (rs.next()){
                OdjelID = rs.getInt(1);
            }
            System.out.println(OdjelID);

            ps3 = con.prepareStatement(query3);
            ps3.setString(1, addImePrezime.getText());
            ps3.setString(2, addOpis.getText());
            ps3.setInt(3, KorisnikID);
            ps3.setInt(4, OdjelID);
            ps3.execute();

            con.close();

        }catch (Exception e){
            e.getMessage();
        }
    }
    private void thirdSafe(){
        try {
            con = DbConnection.getConnection();

            getQuery3();
            insert3();

            con.close();

        }catch (Exception e){
            e.getMessage();
        }
    }
}
