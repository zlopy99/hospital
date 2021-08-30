package Admin;

import Login.Option;
import com.mysql.cj.conf.EnumProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ListOfDoktors {
    private final StringProperty User_Name;
    private final StringProperty Password;
    private final StringProperty Ime_Prezime;
    private final StringProperty Opis;
    private final StringProperty Odjel;

    public ListOfDoktors(String User_Name, String Password, String Ime_Prezime, String Opis, String Odjel){
        this.User_Name = new SimpleStringProperty(User_Name);
        this.Password = new SimpleStringProperty(Password);
        this.Ime_Prezime = new SimpleStringProperty(Ime_Prezime);
        this.Opis = new SimpleStringProperty(Opis);
        this.Odjel = new SimpleStringProperty(Odjel);
    }

    public String getUser_Name() {
        return User_Name.get();
    }

    public StringProperty user_NameProperty() {
        return User_Name;
    }

    public void setUser_Name(String user_Name) {
        this.User_Name.set(user_Name);
    }

    public String getPassword() {
        return "ENCRIPTED";//Password.get();
    }

    public String getPassword2() {
        return Password.get();
    }

    public StringProperty passwordProperty() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password.set(password);
    }

    public String getIme_Prezime() {
        return Ime_Prezime.get();
    }

    public StringProperty ime_PrezimeProperty() {
        return Ime_Prezime;
    }

    public void setIme_Prezime(String ime_Prezime) {
        this.Ime_Prezime.set(ime_Prezime);
    }

    public String getOpis() {
        return Opis.get();
    }

    public StringProperty opisProperty() {
        return Opis;
    }

    public void setOpis(String opis) {
        this.Opis.set(opis);
    }

    public String getOdjel() {
        return Odjel.get();
    }

    public StringProperty odjelProperty() {
        return Odjel;
    }

    public void setOdjel(String odjel) {
        this.Odjel.set(odjel);
    }
}
