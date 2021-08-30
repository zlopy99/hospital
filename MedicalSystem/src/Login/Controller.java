package Login;

import Admin.AdminController;
import DataBase.DbConnection;
import Doktor.DoktorController;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    LoginModul loginModul = new LoginModul();

    @FXML
    private Label chechkCon;

    @FXML
    private TextField Username;

    @FXML
    private ComboBox<Option> ComboBox;

    @FXML
    private Button LoginButton;

    @FXML
    private PasswordField Password;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if(loginModul.isConnected()){
            chechkCon.setText("Connected");
        }

        ComboBox.setItems(FXCollections.observableArrayList(Option.values()));
    }

    // Prijava na Doktora ili Admina
    @FXML
    public void Login(ActionEvent ae) {
        try {
            if(loginModul.isLogin(Username.getText(), Password.getText(), (ComboBox.getValue()).toString())){
                Stage stage = (Stage)LoginButton.getScene().getWindow();
                stage.close();
                switch (((Option)ComboBox.getValue()).toString()){
                    case "Admin":
                        AdminLogin();
                        break;
                    case "Lijecnik":
                        DoktorLogin();
                        break;
                }
            }else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText(null);
                alert.setContentText("Došlo je do greške.\n" +
                        "Provjerite jeste li dobro popunili sva polja i izabrali ulogu.");
                alert.showAndWait();
            }

        }catch (Exception e){
            e.getMessage();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Molimo vas popunite sva polja i odaberite ulogu.(Šifra mora biti minimum 6 karaktera)");
            alert.showAndWait();
        }
    }

    public void DoktorLogin(){
        try {
            Stage doktorStage = new Stage();
            FXMLLoader doktorLoader = new FXMLLoader(getClass().getResource("/Doktor/doktor.fxml"));
            Parent doktorRoot = (Parent) doktorLoader.load();

            DoktorController doktorController = doktorLoader.getController();

            Connection con = DbConnection.getConnection();
            PreparedStatement prep = con.prepareStatement("SELECT korisnik_id FROM korisnik WHERE Korisnicko_ime LIKE ?");
            prep.setString(1,Username.getText());
            ResultSet rs = prep.executeQuery();
            int IDl = 0;
            while (rs.next()){
                IDl = rs.getInt(1);
            }
            doktorController.ImeDoktora(IDl);
            con.close();

            Scene doktorScene = new Scene(doktorRoot, 1280, 720);
            doktorStage.setScene(doktorScene);
            doktorStage.setResizable(false);
            doktorStage.setTitle("Lijecnik Inbox");
            doktorStage.show();

        }catch (Exception e){
            e.getMessage();
            System.out.println(e);
        }
    }

    public void AdminLogin(){
        try {
            Stage adminStage = new Stage();
            FXMLLoader adminLoader = new FXMLLoader(getClass().getResource("/Admin/admin.fxml"));
            Parent adminRoot = (Parent) adminLoader.load();

            AdminController adminController = adminLoader.getController();

            Connection con = DbConnection.getConnection();
            PreparedStatement prep = con.prepareStatement("SELECT korisnik_id FROM korisnik WHERE Korisnicko_ime LIKE ?");
            prep.setString(1,Username.getText());
            ResultSet rs = prep.executeQuery();
            int IDk = 0;
            while (rs.next()){
                IDk = rs.getInt(1);
            }
            adminController.ImeAdmina(IDk);
            con.close();

            Scene adminScene = new Scene(adminRoot, 1280, 720);
            adminStage.setScene(adminScene);
            adminStage.setResizable(false);
            adminStage.setTitle("Admin Inbox");
            adminStage.show();

        }catch (Exception e){
            e.getMessage();
            System.out.println(e);
        }
    }
}
