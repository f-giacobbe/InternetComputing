package it.unical.dimes.reti.soluzioni_reti_tutte_java._20230111;
import java.io.Serializable;

public class Misura implements Serializable {
    private static final long serialVersionUID = 1L;

    private String idSensore;
    private double valore;
    private long timestamp;

    public Misura(String idSensore, double valore, long timestamp) {
        this.idSensore = idSensore;
        this.valore = valore;
        this.timestamp = timestamp;
    }

    public String getIdSensore() {
        return idSensore;
    }

    public double getValore() {
        return valore;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public String toString() {
        return "Misura{idSensore='" + idSensore + "', valore=" + valore +
               ", timestamp=" + timestamp + "}";
    }
}
