package Doktor;

import DataBase.DbConnection;
import javafx.collections.FXCollections;
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
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

public class EditPacijentController implements Initializable {

    @FXML
    private TextField ImePacijentaEdit;

    @FXML
    private TextField JMBGPacijentEdit;

    @FXML
    private ComboBox<Zdravstveno> OsiguranjePacijentaEdit;

    @FXML
    private ComboBox<Cjepivo> CovidPacijentaEdit;

    @FXML
    private TextField PregledPacijentaEdit;

    @FXML
    private DatePicker DatumPacijentaEdit;

    @FXML
    private Button cancelEditPacijent;

    Connection con = null;
    PreparedStatement prepS = null;
    ResultSet rs = null;
    String bivsiJMBG = null;
    String query = null;
    String query2 = null;
    int PacijentId = 0;

    @FXML
    void cancelEditPacijent(MouseEvent event) {
        Stage stage = (Stage)cancelEditPacijent.getScene().getWindow();
        stage.close();
    }

    public void getSelection(String Pacijent, String JMBG, String Pregled, String Osigu,
                             String Cjep, String Date){
        ImePacijentaEdit.setText(Pacijent);
        JMBGPacijentEdit.setText(JMBG);
        PregledPacijentaEdit.setText(Pregled);
        OsiguranjePacijentaEdit.setValue(Zdravstveno.valueOf(Osigu));
        CovidPacijentaEdit.setValue(Cjepivo.valueOf(Cjep));
        DatumPacijentaEdit.setValue(LocalDate.parse(Date));
        bivsiJMBG = JMBG;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        OsiguranjePacijentaEdit.setItems(FXCollections.observableArrayList(Zdravstveno.values()));
        CovidPacijentaEdit.setItems(FXCollections.observableArrayList(Cjepivo.values()));
    }

    int jelSveOK = 0;
    @FXML
    void saveEdit(MouseEvent event){
        try {
            con = DbConnection.getConnection();
            Statement stm = con.createStatement();
            rs = stm.executeQuery("SELECT JMBG FROM pacijent");
            ArrayList<String> Lista = new ArrayList<String>();
            while (rs.next()){
                Lista.add(rs.getString(1));
            }
            rs.close();
            stm.close();

            if(!(Lista.contains(JMBGPacijentEdit.getText())) || (JMBGPacijentEdit.getText().equalsIgnoreCase(bivsiJMBG))){
                jelSveOK = 0;
                getQuery1();
                edit1();
                secondSafveEdit();

                if(jelSveOK == 2){
                    Alert alert1 = new Alert(Alert.AlertType.CONFIRMATION);
                    alert1.setHeaderText(null);
                    alert1.setContentText("Želite li spremiti promijene?");
                    Optional<ButtonType> result = alert1.showAndWait();

                    if((result.get().getButtonData().toString().equalsIgnoreCase("OK_DONE"))){
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setHeaderText(null);
                        alert.setContentText("Uspiješno ste updetali pacijenta.");
                        alert.showAndWait();
                    }
                }

            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("JMBG već postoji.");
                alert.showAndWait();
            }

            con.close();
        }catch (Exception e){
            e.getMessage();
            System.out.println("Error5: "+e);
        }
    }


    private void getQuery1(){
        query="UPDATE `pacijent` SET `Ime_Prezime`=?,`JMBG`= ?, `Zdravstveno_Osiguranje` = ?, `Covid_cjepivo` = ? WHERE `JMBG` = ?";
    }
    private void edit1(){
        try {
            if(!(ImePacijentaEdit.getText().isEmpty() || JMBGPacijentEdit.getText().isEmpty()))
            {
                con = DbConnection.getConnection();
                prepS = con.prepareStatement(query);
                prepS.setString(1, ImePacijentaEdit.getText());
                prepS.setString(2, JMBGPacijentEdit.getText());
                prepS.setString(3, OsiguranjePacijentaEdit.getValue().toString());
                prepS.setString(4, CovidPacijentaEdit.getValue().toString());
                prepS.setString(5, bivsiJMBG);
                prepS.execute();

                prepS.close();
                con.close();
                jelSveOK += 1;
            }
            else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Sva polja moraju bit popunjena(Pregled je opcionalan).");
                alert.showAndWait();
            }
        }catch (Exception e){
            e.getMessage();
            System.out.println("Error4: "+e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Sva polja moraju bit popunjena(Pregled je opcionalan).");
            alert.showAndWait();
        }
    }


    private void getQuery2(){
        query2="UPDATE `pregled` SET `Opis`=?,`Datum`=? " +
                "WHERE `id_pacijenta` = ?";
    }
    int IDkorisnika;
    private void edit2(){
        try {
            con = DbConnection.getConnection();
            prepS = con.prepareStatement("SELECT pacijent_id FROM pacijent WHERE JMBG  LIKE ?");
            prepS.setString(1,JMBGPacijentEdit.getText());
            rs = prepS.executeQuery();
            PacijentId = 0;
            while (rs.next()){
                PacijentId = rs.getInt(1);
            }
            rs.close();
            prepS.close();

            if(!(DatumPacijentaEdit.getValue().toString().isEmpty())){
                prepS = con.prepareStatement(query2);
                prepS.setString(1, PregledPacijentaEdit.getText());
                prepS.setString(2, DatumPacijentaEdit.getValue().toString());
                prepS.setInt(3, PacijentId);
                prepS.execute();

                prepS.close();
                jelSveOK += 1;

            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Sva polja moraju bit popunjena(Pregled je opcionalan).");
                alert.showAndWait();
            }

            con.close();

        }catch (Exception e){
            e.getMessage();
            System.out.println("Error3: "+e);
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Sva polja moraju bit popunjena(Pregled je opcionalan).");
            alert.showAndWait();
        }
    }
    private void secondSafveEdit(){
        try {
            //con = DbConnection.getConnection();

            getQuery2();
            edit2();

            //con.close();
        }catch (Exception e){
            e.getMessage();
            System.out.println("Error2: "+e);
        }
    }
}
