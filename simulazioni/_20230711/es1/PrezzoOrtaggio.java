package it.unical.dimes.reti.simulazioni._20230711.es1;

public class PrezzoOrtaggio {
    private double prezzo;
    private int anno;

    public PrezzoOrtaggio(double prezzo, int anno) {
        this.prezzo = prezzo;
        this.anno = anno;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public int getAnno() {
        return anno;
    }
}
