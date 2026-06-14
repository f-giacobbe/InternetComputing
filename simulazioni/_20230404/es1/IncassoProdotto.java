package it.unical.dimes.reti.simulazioni._20230404.es1;

import java.io.Serializable;

public class IncassoProdotto implements Serializable {
    private String nomeVino;
    private int quantita;
    private double importo;

    public IncassoProdotto(String nomeVino, int quantita, double importo) {
        this.nomeVino = nomeVino;
        this.quantita = quantita;
        this.importo = importo;
    }

    public String getNomeVino() {
        return nomeVino;
    }

    public int getQuantita() {
        return quantita;
    }

    public double getImporto() {
        return importo;
    }
}
