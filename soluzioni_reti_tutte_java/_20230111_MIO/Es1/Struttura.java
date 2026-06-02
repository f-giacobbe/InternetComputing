package it.unical.dimes.reti.soluzioni_reti_tutte_java._20230111_MIO.Es1;

import java.io.Serializable;

public class Struttura implements Serializable {
    private String nome;
    private String citta;
    private int stelle;
    private int posti;

    public Struttura(String nome, String citta, int stelle, int posti) {
        this.nome = nome;
        this.citta = citta;
        this.stelle = stelle;
        this.posti = posti;
    }

    public String getNome() {
        return nome;
    }

    public String getCitta() {
        return citta;
    }

    public int getStelle() {
        return stelle;
    }

    public int getPosti() {
        return posti;
    }
}
