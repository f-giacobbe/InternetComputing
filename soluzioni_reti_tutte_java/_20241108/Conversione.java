package it.unical.dimes.reti.soluzioni_reti_tutte_java._20241108;
import java.io.Serializable;

public class Conversione implements Serializable {
    private static final long serialVersionUID = 1L;

    private String idClient;
    private String valutaDa;
    private String valutaA;
    private double importo;
    private double importoConvertito;
    private double tasso;
    private long timestamp;

    public Conversione(String idClient, String valutaDa, String valutaA,
                       double importo, double importoConvertito, double tasso) {
        this.idClient = idClient;
        this.valutaDa = valutaDa;
        this.valutaA = valutaA;
        this.importo = importo;
        this.importoConvertito = importoConvertito;
        this.tasso = tasso;
        this.timestamp = System.currentTimeMillis();
    }

    public double getTasso() { return tasso; }
    public long getTimestamp() { return timestamp; }
    public double getImportoConvertito() { return importoConvertito; }

    public String toString() {
        return idClient + ": " + importo + " " + valutaDa + " -> " + importoConvertito + " " + valutaA +
               " con tasso " + tasso;
    }
}
