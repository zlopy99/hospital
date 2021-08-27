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
    /*
    private final StringProperty LOGIRANI_DOKTOR;
    private final StringProperty LOZINKA;
    private final StringProperty EMAIL;
    private final StringProperty GODISTE;
    private final StringProperty PREBIVALISTE;
    private final StringProperty SPOL;
    private final StringProperty OPIS;

    public ListOfDoktors(String LOGIRANI_DOKTOR, String LOZINKA, String EMAIL, String GODISTE,
                         String PREBIVALISTE, String SPOL, String OPIS){

        this.LOGIRANI_DOKTOR = new SimpleStringProperty(LOGIRANI_DOKTOR);
        this.LOZINKA = new SimpleStringProperty(LOZINKA);
        this.EMAIL = new SimpleStringProperty(EMAIL);
        this.GODISTE = new SimpleStringProperty(GODISTE);
        this.PREBIVALISTE = new SimpleStringProperty(PREBIVALISTE);
        this.SPOL = new SimpleStringProperty(SPOL);
        this.OPIS = new SimpleStringProperty(OPIS);
    }

    public String getLOGIRANI_DOKTOR() {
        return LOGIRANI_DOKTOR.get();
    }

    public StringProperty LOGIRANI_DOKTORProperty() {
        return LOGIRANI_DOKTOR;
    }

    public void setLOGIRANI_DOKTOR(String LOGIRANI_DOKTOR) {
        this.LOGIRANI_DOKTOR.set(LOGIRANI_DOKTOR);
    }

    public String getLOZINKA() {
        return LOZINKA.get();
    }

    public StringProperty LOZINKAProperty() {
        return LOZINKA;
    }

    public void setLOZINKA(String LOZINKA) {
        this.LOZINKA.set(LOZINKA);
    }

    public String getEMAIL() {
        return EMAIL.get();
    }

    public StringProperty EMAILProperty() {
        return EMAIL;
    }

    public void setEMAIL(String EMAIL) {
        this.EMAIL.set(EMAIL);
    }

    public String getGODISTE() {
        return GODISTE.get();
    }

    public StringProperty GODISTEProperty() {
        return GODISTE;
    }

    public void setGODISTE(String GODISTE) {
        this.GODISTE.set(GODISTE);
    }

    public String getPREBIVALISTE() {
        return PREBIVALISTE.get();
    }

    public StringProperty PREBIVALISTEProperty() {
        return PREBIVALISTE;
    }

    public void setPREBIVALISTE(String PREBIVALISTE) {
        this.PREBIVALISTE.set(PREBIVALISTE);
    }

    public String getSPOL() {
        return SPOL.get();
    }

    public StringProperty SPOLProperty() {
        return SPOL;
    }

    public void setSPOL(String SPOL) {
        this.SPOL.set(SPOL);
    }

    public String getOPIS() {
        return OPIS.get();
    }

    public StringProperty OPISProperty() {
        return OPIS;
    }

    public void setOPIS(String OPIS) {
        this.OPIS.set(OPIS);
    }
     */
}
