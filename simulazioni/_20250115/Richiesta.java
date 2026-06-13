package it.unical.dimes.reti.simulazioni._20250115;

import java.io.Serializable;

public class Richiesta implements Serializable {
    private int idClient;
    private String codice;
    private Partita partita;

    public Richiesta(int idClient, String codice, Partita partita) {
        this.idClient = idClient;
        this.codice = codice;
        this.partita = partita;
    }

    public int getIdClient() {
        return idClient;
    }

    public String getCodice() {
        return codice;
    }

    public Partita getPartita() {
        return partita;
    }
}
