package it.unical.dimes.reti.simulazioni._20230404.es2;

import java.io.Serializable;

public class Offerta implements Serializable, Comparable<Offerta> {
    private String cf;
    private int idAsta;
    private double importo;

    public Offerta(String cf, int idAsta, double importo) {
        this.cf = cf;
        this.idAsta = idAsta;
        this.importo = importo;
    }

    public String getCf() {
        return cf;
    }

    public int getIdAsta() {
        return idAsta;
    }

    public double getImporto() {
        return importo;
    }

    @Override
    public int compareTo(Offerta o) {
        return Double.compare(o.getImporto(), this.importo);
    }
}
