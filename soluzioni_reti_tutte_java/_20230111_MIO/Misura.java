package it.unical.dimes.reti.soluzioni_reti_tutte_java._20230111_MIO;

import java.io.Serializable;
import java.util.Calendar;

public class Misura implements Serializable {
    private String idSensore;
    private double valore;
    private Calendar timestamp;

    public Misura(String s, double v, Calendar ts) {
        idSensore = s;
        valore = v;
        timestamp = ts;
    }

    public String getIdSensore() {
        return idSensore;
    }

    public void setIdSensore(String idSensore) {
        this.idSensore = idSensore;
    }

    public double getValore() {
        return valore;
    }

    public void setValore(double valore) {
        this.valore = valore;
    }

    public Calendar getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Calendar timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return String.format("%s %f %s", idSensore, valore, timestamp);
    }
}
