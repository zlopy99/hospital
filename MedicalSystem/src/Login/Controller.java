package Login;

import Admin.AdminController;
import Admin.EditDoktorController;
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
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
                alert.setContentText("Molimo vas popunite sva polja.(Šifra mora biti minimum 6 karaktera)");
                alert.showAndWait();
            }

        }catch (Exception e){
            e.getMessage();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Molimo vas popunite sva polja.(Šifra mora biti minimum 6 karaktera)");
            alert.showAndWait();
        }
    }

    public void DoktorLogin(){
        try {
            //FXMLLoader doktorLoader = new FXMLLoader();
            Stage doktorStage = new Stage();
            //Parent doktorRoot = FXMLLoader.load(getClass().getResource("/Doktor/doktor.fxml"));
            //Pane doktorRoot = (Pane)doktorLoader.load(getClass().getResource("/Doktor/doktor.fxml").openStream());
            FXMLLoader doktorLoader = new FXMLLoader(getClass().getResource("/Admin/admin.fxml"));
            Parent doktorRoot = (Parent) doktorLoader.load();

            DoktorController doktorController = doktorLoader.getController();

            Scene doktorScene = new Scene(doktorRoot, 1280, 720);
            doktorStage.setScene(doktorScene);
            doktorStage.setResizable(false);
            doktorStage.setTitle("Lijecnik Inbox");
            doktorStage.show();

        }catch (Exception e){
            e.getMessage();
        }
    }

    public void AdminLogin(){
        try {
            //FXMLLoader adminLoader = new FXMLLoader();
            Stage adminStage = new Stage();
            //Parent adminRoot = FXMLLoader.load(getClass().getResource("/Admin/admin.fxml"));
            //Pane adminRoot = (Pane)adminLoader.load(getClass().getResource("/Admin/admin.fxml").openStream());
            FXMLLoader adminLoader = new FXMLLoader(getClass().getResource("/Admin/admin.fxml"));
            Parent adminRoot = (Parent) adminLoader.load();

            AdminController adminController = adminLoader.getController();

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
