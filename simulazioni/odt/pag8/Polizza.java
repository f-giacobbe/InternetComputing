package it.unical.dimes.reti.simulazioni.odt.pag8;

import java.io.Serializable;

public class Polizza implements Serializable {
    private String codice;
    private double premio;
    private double copertura;

    public Polizza(String codice, double premio, double copertura) {
        this.codice = codice;
        this.premio = premio;
        this.copertura = copertura;
    }

    public String getCodice() {
        return codice;
    }

    public double getPremio() {
        return premio;
    }

    public double getCopertura() {
        return copertura;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Polizza p) {
            return codice.equals(p.getCodice());
        }
        return false;
    }
}
