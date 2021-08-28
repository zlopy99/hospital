package Doktor;

import DataBase.DbConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
    private Button DokSave;

    @FXML
    private Button DokClear;

    Connection con = null;
    PreparedStatement prepS = null;
    ResultSet rs = null;
    int DokKorisnikID = 0;
    int DoktorID = 0;
    int OdjelID = 0;

    // Doktor korisnicko ime
    public void ImeDoktora(int DokID){
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
                Lozinka.setText(rs.getString(2));
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
}
