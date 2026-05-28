package it.unical.dimes.reti.soluzioni_reti_tutte_java._20240317;
import java.io.Serializable;

public class StatoSensore implements Serializable {
    private static final long serialVersionUID = 1L;

    private String idSensore;
    private int numeroProgressivo;
    private double temperaturaAria;
    private double umiditaSuolo;
    private long timestamp;

    public StatoSensore(String idSensore, double temperaturaAria, double umiditaSuolo) {
        this.idSensore = idSensore;
        this.temperaturaAria = temperaturaAria;
        this.umiditaSuolo = umiditaSuolo;
        this.timestamp = System.currentTimeMillis();
    }

    public String getIdSensore() { return idSensore; }
    public int getNumeroProgressivo() { return numeroProgressivo; }
    public double getTemperaturaAria() { return temperaturaAria; }
    public double getUmiditaSuolo() { return umiditaSuolo; }
    public long getTimestamp() { return timestamp; }

    public void setNumeroProgressivo(int numeroProgressivo) {
        this.numeroProgressivo = numeroProgressivo;
    }

    public String toString() {
        return idSensore + "#" + numeroProgressivo + "#T=" + temperaturaAria + ",U=" + umiditaSuolo;
    }
}
