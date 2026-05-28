package it.unical.dimes.reti.soluzioni_reti_tutte_java._20210318;
import java.io.Serializable;

public class RichiestaAcquisto implements Serializable {
    private static final long serialVersionUID = 1L;

    private String idProdotto;
    private int quantita;
    private double prezzoMassimo;

    public RichiestaAcquisto(String idProdotto, int quantita, double prezzoMassimo) {
        this.idProdotto = idProdotto;
        this.quantita = quantita;
        this.prezzoMassimo = prezzoMassimo;
    }

    public String getIdProdotto() { return idProdotto; }
    public int getQuantita() { return quantita; }
    public double getPrezzoMassimo() { return prezzoMassimo; }
}
