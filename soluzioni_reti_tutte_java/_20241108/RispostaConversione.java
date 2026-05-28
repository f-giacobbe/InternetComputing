package it.unical.dimes.reti.soluzioni_reti_tutte_java._20241108;
import java.io.Serializable;

public class RispostaConversione implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean ok;
    private String messaggio;
    private double importoConvertito;
    private double tasso;

    public RispostaConversione(boolean ok, String messaggio, double importoConvertito, double tasso) {
        this.ok = ok;
        this.messaggio = messaggio;
        this.importoConvertito = importoConvertito;
        this.tasso = tasso;
    }

    public boolean isOk() { return ok; }
    public String getMessaggio() { return messaggio; }
    public double getImportoConvertito() { return importoConvertito; }
    public double getTasso() { return tasso; }

    public String toString() {
        return messaggio + " - importo=" + importoConvertito + ", tasso=" + tasso;
    }
}
