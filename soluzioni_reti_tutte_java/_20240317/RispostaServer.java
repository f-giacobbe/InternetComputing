package it.unical.dimes.reti.soluzioni_reti_tutte_java._20240317;
import java.io.Serializable;

public class RispostaServer implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean accettato;
    private String messaggio;
    private int numeroProgressivo;

    public RispostaServer(boolean accettato, String messaggio, int numeroProgressivo) {
        this.accettato = accettato;
        this.messaggio = messaggio;
        this.numeroProgressivo = numeroProgressivo;
    }

    public boolean isAccettato() { return accettato; }
    public String getMessaggio() { return messaggio; }
    public int getNumeroProgressivo() { return numeroProgressivo; }

    public String toString() {
        return messaggio + " - progressivo=" + numeroProgressivo;
    }
}
