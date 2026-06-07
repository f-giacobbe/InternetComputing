package it.unical.dimes.reti.simulazioni._20230711.es2;

import java.io.Serializable;

public class OffertaNegozio implements Serializable, Comparable<OffertaNegozio> {
    private String piva;
    private String nazione;
    private String prodotto;
    private double prezzo;
    private int quantita;

    public OffertaNegozio(String piva, String nazione, String prodotto, double prezzo, int quantita) {
        this.piva = piva;
        this.nazione = nazione;
        this.prodotto = prodotto;
        this.prezzo = prezzo;
        this.quantita = quantita;
    }

    public String getPiva() {
        return piva;
    }

    public String getNazione() {
        return nazione;
    }

    public String getProdotto() {
        return prodotto;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public int getQuantita() {
        return quantita;
    }

    @Override
    public int compareTo(OffertaNegozio o) {
        return Double.compare(prezzo, o.getPrezzo());
    }

    // Usato per cancellaOfferta
    @Override
    public boolean equals(Object o) {
        if (o instanceof OffertaNegozio on) {
            return piva.equals(on.getPiva()) && nazione.equals(on.getNazione()) && prodotto.equals(on.getProdotto()) && prezzo == on.getPrezzo();
        }

        return false;
    }

    @Override
    public String toString() {
        return "OffertaNegozio{" +
                "piva='" + piva + '\'' +
                ", nazione='" + nazione + '\'' +
                ", prodotto='" + prodotto + '\'' +
                ", prezzo=" + prezzo +
                ", quantita=" + quantita +
                '}';
    }
}
