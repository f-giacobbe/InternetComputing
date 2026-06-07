package it.unical.dimes.reti.simulazioni._20230711.es1;

import java.io.Serializable;

public class PrezzoProdotto implements Serializable {
    private String partitaIva;
    private String nomeOrtaggio;
    private double prezzo;

    public PrezzoProdotto(String partitaIva, String nomeOrtaggio, double prezzo) {
        this.partitaIva = partitaIva;
        this.nomeOrtaggio = nomeOrtaggio;
        this.prezzo = prezzo;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public String getNomeOrtaggio() {
        return nomeOrtaggio;
    }

    public double getPrezzo() {
        return prezzo;
    }
}
