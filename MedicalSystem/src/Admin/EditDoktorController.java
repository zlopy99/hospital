package Admin;

import DataBase.DbConnection;
import Login.Option;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class EditDoktorController {

    @FXML
    private TextField editKorisnikIme;

    @FXML
    private TextField editLozinka;

    @FXML
    private TextField editImePrezime;

    @FXML
    private TextField editOpis;

    @FXML
    private TextField editOdjel;

    @FXML
    private Button cancel;

    Connection con = null;
    ResultSet rs = null;
    PreparedStatement ps = null;
    PreparedStatement ps2 = null;
    String query = null;
    String query2 = null;

    //public void initialize(URL url, ResourceBundle resourceBundle) { }

    @FXML
    void cancelEdit(MouseEvent event) {
        Stage stage = (Stage)cancel.getScene().getWindow();
        stage.close();
    }

    String bivseIme = null;
    public void getSelection(String korisnik, String lozinka, String imeP, String odjel,
                             String opis){
        editKorisnikIme.setText(korisnik);
        editLozinka.setText(null);
        editImePrezime.setText(imeP);
        editOdjel.setText(odjel);
        editOpis.setText(opis);

        bivseIme = editKorisnikIme.getText();
    }

    int jelSveOK = 0;
    @FXML
    void saveEdit(MouseEvent event){
        try {
            con = DbConnection.getConnection();
            Statement stm = con.createStatement();
            ResultSet rs = stm.executeQuery("SELECT Korisnicko_ime FROM korisnik");
            ArrayList<String> Lista = new ArrayList<String>();
            while (rs.next()){
                Lista.add(rs.getString(1));
            }

            if(!(Lista.contains(editKorisnikIme.getText())) || (editKorisnikIme.getText().equalsIgnoreCase(bivseIme))){
                jelSveOK = 0;
                getQuery1();
                edit1();
                secondSafveEdit();
                thirdSafeEdit();

                if(jelSveOK == 3){
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Uspiješno ste updetali doktora.");
                    alert.showAndWait();
                }

            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Korisnik već postoji.");
                alert.showAndWait();
            }

            con.close();
        }catch (Exception e){
            e.getMessage();
            System.out.println("Error5: "+e);
        }
    }


    private void getQuery1(){
        query="UPDATE `korisnik` SET `Korisnicko_ime`=?,`Lozinka`=PASSWORD(?) WHERE `Korisnicko_ime` = ?";
    }
    private void edit1(){
        try {
            if(!(editKorisnikIme.getText().isEmpty() || editLozinka.getText().isEmpty()))
            {
                if(editLozinka.getText().length() >= 6){
                    ps = con.prepareStatement(query);
                    ps.setString(1, editKorisnikIme.getText());
                    ps.setString(2, editLozinka.getText());
                    ps.setString(3, bivseIme);
                    ps.execute();

                    jelSveOK += 1;
                }else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Lozinka mora biti minimum 6 znakova.");
                    alert.showAndWait();
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Sva polja moraju bit popunjena(Opis je opcionalan).");
                alert.showAndWait();
            }
            con.close();

        }catch (Exception e){
            e.getMessage();
            System.out.println("Error4: "+e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Lozinka mora biti minimum 6 znakova.");
            alert.showAndWait();
        }
    }


    private void getQuery2(){
        query2="UPDATE `liječnik` SET `Ime_Prezime`=?,`Opis`=? " +
                "WHERE `korisnik_id` = ?";
    }
    int IDkorisnika;
    private void edit2(){
        try {
            PreparedStatement pomPrep = con.prepareStatement("SELECT korisnik_id FROM korisnik WHERE Korisnicko_ime  LIKE ?");
            pomPrep.setString(1,editKorisnikIme.getText());
            ResultSet pomRS = pomPrep.executeQuery();
            IDkorisnika = 0;
            while (pomRS.next()){
                IDkorisnika = pomRS.getInt(1);
            }

            if(!(editImePrezime.getText().isEmpty())){
                ps2 = con.prepareStatement(query2);
                ps2.setString(1, editImePrezime.getText());
                ps2.setString(2, editOpis.getText());
                ps2.setInt(3, IDkorisnika);
                ps2.execute();

                jelSveOK += 1;

            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Sva polja moraju bit popunjena(Opis je opcionalan).");
                alert.showAndWait();
            }

            con.close();

        }catch (Exception e){
            e.getMessage();
            System.out.println("Error3: "+e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Molimo vas upišite broj u polje godište.");
            alert.showAndWait();
        }
    }
    private void secondSafveEdit(){
        try {
            con = DbConnection.getConnection();

            getQuery2();
            edit2();

            con.close();

        }catch (Exception e){
            e.getMessage();
            System.out.println("Error2: "+e);
        }
    }


    private void getQuery3(){
        query2="UPDATE `odjel` SET `Naziv`=? " +
                "WHERE `odjel_id` = ?";
    }
    private void edit3(){
        try {
            PreparedStatement pomPrep = con.prepareStatement("SELECT odjel_id FROM liječnik WHERE korisnik_id LIKE ?");
            pomPrep.setInt(1, IDkorisnika);
            ResultSet pomRS = pomPrep.executeQuery();
            int IDodjela = 0;
            while (pomRS.next()){
                IDodjela = pomRS.getInt(1);
            }

            if(!(editOdjel.getText().isEmpty())){
                ps2 = con.prepareStatement(query2);
                ps2.setString(1, editOdjel.getText());
                ps2.setInt(2, IDodjela);
                ps2.execute();

                jelSveOK += 1;

            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Sva polja moraju bit popunjena(Opis je opcionalan).");
                alert.showAndWait();
            }

            con.close();

        }catch (Exception e){
            e.getMessage();
            System.out.println("Error3: "+e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Molimo vas upišite broj u polje godište.");
            alert.showAndWait();
        }
    }
    private void thirdSafeEdit(){
        try {
            con = DbConnection.getConnection();

            getQuery3();
            edit3();

            con.close();

        }catch (Exception e){
            e.getMessage();
            System.out.println("Error2: "+e);
        }
    }
}
