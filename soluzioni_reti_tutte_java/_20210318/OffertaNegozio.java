package it.unical.dimes.reti.soluzioni_reti_tutte_java._20210318;
import java.io.Serializable;

public class OffertaNegozio implements Serializable {
    private static final long serialVersionUID = 1L;

    private String idNegozio;
    private double prezzoAcquisto;

    public OffertaNegozio(String idNegozio, double prezzoAcquisto) {
        this.idNegozio = idNegozio;
        this.prezzoAcquisto = prezzoAcquisto;
    }

    public String getIdNegozio() { return idNegozio; }
    public double getPrezzoAcquisto() { return prezzoAcquisto; }

    public String toString() {
        return "Negozio=" + idNegozio + ", prezzo=" + prezzoAcquisto;
    }
}
