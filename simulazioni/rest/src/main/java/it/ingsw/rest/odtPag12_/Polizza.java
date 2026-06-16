package it.ingsw.rest.odtPag12_;

import jakarta.persistence.Id;

public class Polizza {
    @Id
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
}
