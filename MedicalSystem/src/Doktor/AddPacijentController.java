package Doktor;

import DataBase.DbConnection;
import Login.Option;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;

import javax.lang.model.type.NullType;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AddPacijentController implements Initializable {

    @FXML
    private TextField PacijentIme;

    @FXML
    private TextField JMBGPacijent;

    @FXML
    private ComboBox<Zdravstveno> OsiguranjePacijent;

    @FXML
    private ComboBox<Cjepivo> CovidPacijent;

    @FXML
    private TextField PregledPacijent;

    @FXML
    private DatePicker DatumPacijent;

    Connection con = null;
    PreparedStatement prepS = null;
    ResultSet rs = null;
    int DokId = 0;
    int PacijentId = 0;

    public void getID(int pom){
        DokId = pom;
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {

        OsiguranjePacijent.setItems(FXCollections.observableArrayList(Zdravstveno.values()));
        CovidPacijent.setItems(FXCollections.observableArrayList(Cjepivo.values()));
    }

    @FXML
    void cleanP(MouseEvent event) {
        PacijentIme.setText(null);
        JMBGPacijent.setText(null);
        OsiguranjePacijent.setValue(null);
        CovidPacijent.setValue(null);
        PregledPacijent.setText(null);
        DatumPacijent.setValue(null);
    }

    @FXML
    void saveP(MouseEvent event) {
        try {
            ArrayList<String> Lista = new ArrayList<String>();
            con = DbConnection.getConnection();
            prepS = con.prepareStatement("SELECT JMBG FROM pacijent");
            rs = prepS.executeQuery();
            while (rs.next()){
                Lista.add(rs.getString(1));
            }
            rs.close();
            prepS.close();
            con.close();

            if(PacijentIme.getText().isEmpty() || JMBGPacijent.getText().isEmpty() || DatumPacijent.getValue().toString().isEmpty() ||
            OsiguranjePacijent.getValue().toString().isEmpty() || CovidPacijent.getValue().toString().isEmpty()){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Popuniti sva polja(Pregled opcionalan).");
                alert.showAndWait();
            }else {
                if(Lista.contains(JMBGPacijent.getText())){
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText(null);
                    alert.setContentText("Matični broj već postoji.");
                    alert.showAndWait();
                }else {
                    insert1();
                    secondInsert();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText("Uspiješno ste unijeli pacijenta u bazu.");
                    alert.showAndWait();
                }
            }

        }catch (Exception e){
            e.getMessage();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Popuniti sva polja(Pregled opcionalan).");
            alert.showAndWait();
        }
    }

    private void insert1(){
        try {
            con = DbConnection.getConnection();
            prepS = con.prepareStatement("INSERT INTO `pacijent`(`Ime_Prezime`, `JMBG`, `Zdravstveno_osiguranje`, `Covid_cjepivo`) VALUES (?,?,?,?)");
            prepS.setString(1,PacijentIme.getText());
            prepS.setString(2,JMBGPacijent.getText());
            prepS.setString(3,OsiguranjePacijent.getValue().toString());
            prepS.setString(4,CovidPacijent.getValue().toString());
            prepS.execute();

            prepS.close();
            con.close();

        }catch (Exception e){
            e.getMessage();
            System.out.println(e);
        }
    }
    private void insert2(){
        try {
            con = DbConnection.getConnection();
            prepS = con.prepareStatement("SELECT pacijent_id FROM pacijent WHERE JMBG LIKE ?");
            prepS.setString(1,JMBGPacijent.getText());
            rs = prepS.executeQuery();
            while (rs.next()){
                PacijentId = rs.getInt(1);
            }
            //System.out.println(PacijentId);
            prepS.close();
            rs.close();
            con.close();

            con = DbConnection.getConnection();
            prepS = con.prepareStatement("INSERT INTO `pregled`(`id_lijecnika`, `id_pacijenta`, `Opis`, `Datum`) VALUES (?,?,?,?)");
            prepS.setInt(1,DokId);
            prepS.setInt(2,PacijentId);
            prepS.setString(3,PregledPacijent.getText());
            prepS.setString(4, DatumPacijent.getValue().toString());
            prepS.execute();

            prepS.close();
            con.close();

        }catch (Exception e){
            e.getMessage();
            System.out.println(e);
        }
    }
    private void secondInsert(){
        insert2();
    }
}
