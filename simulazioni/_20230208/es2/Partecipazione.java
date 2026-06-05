package it.unical.dimes.reti.simulazioni._20230208.es2;

import java.io.Serializable;

public class Partecipazione implements Serializable {
    private int idConcorso;
    private String nome;
    private String cognome;
    private String cf;
    private String cv;

    public Partecipazione(int idConcorso, String nome, String cognome, String cf, String cv) {
        this.idConcorso = idConcorso;
        this.nome = nome;
        this.cognome = cognome;
        this.cf = cf;
        this.cv = cv;
    }

    public int getIdConcorso() {
        return idConcorso;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getCf() {
        return cf;
    }

    public String getCv() {
        return cv;
    }
}
