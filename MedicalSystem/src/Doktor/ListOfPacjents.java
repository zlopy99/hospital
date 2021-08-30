package Doktor;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.DatePicker;

import java.text.SimpleDateFormat;

public class ListOfPacjents {
    private final IntegerProperty DoktorID;
    private final StringProperty Datum;
    private final StringProperty Pregled;
    private final StringProperty Pacijent;
    private final StringProperty JMBG;
    private final StringProperty Osiguranje;
    private final StringProperty Cjepivo;

    public ListOfPacjents(int DoktorID, String Datum, String Pregled, String Pacijent, String JMBG, String Osiguranje, String Cjepivo){
        this.DoktorID = new SimpleIntegerProperty(DoktorID);
        this.Datum = new SimpleStringProperty(Datum);
        this.Pregled = new SimpleStringProperty(Pregled);
        this.Pacijent = new SimpleStringProperty(Pacijent);
        this.JMBG = new SimpleStringProperty(JMBG);
        this.Osiguranje = new SimpleStringProperty(Osiguranje);
        this.Cjepivo = new SimpleStringProperty(Cjepivo);
    }

    public int getDoktorID() {
        return DoktorID.get();
    }

    public IntegerProperty doktorIDProperty() {
        return DoktorID;
    }

    public void setDoktorID(int doktorID) {
        this.DoktorID.set(doktorID);
    }

    public String getDatum() {
        return Datum.get();
    }

    public StringProperty datumProperty() {
        return Datum;
    }

    public void setDatum(String datum) {
        this.Datum.set(datum);
    }

    public String getPregled() {
        return Pregled.get();
    }

    public StringProperty pregledProperty() {
        return Pregled;
    }

    public void setPregled(String pregled) {
        this.Pregled.set(pregled);
    }

    public String getPacijent() {
        return Pacijent.get();
    }

    public StringProperty pacijentProperty() {
        return Pacijent;
    }

    public void setPacijent(String pacijent) {
        this.Pacijent.set(pacijent);
    }

    public String getJMBG() {
        return JMBG.get();
    }

    public StringProperty JMBGProperty() {
        return JMBG;
    }

    public void setJMBG(String JMBG) {
        this.JMBG.set(JMBG);
    }

    public String getOsiguranje() {
        return Osiguranje.get();
    }

    public StringProperty osiguranjeProperty() {
        return Osiguranje;
    }

    public void setOsiguranje(String osiguranje) {
        this.Osiguranje.set(osiguranje);
    }

    public String getCjepivo() {
        return Cjepivo.get();
    }

    public StringProperty cjepivoProperty() {
        return Cjepivo;
    }

    public void setCjepivo(String cjepivo) {
        this.Cjepivo.set(cjepivo);
    }
}
