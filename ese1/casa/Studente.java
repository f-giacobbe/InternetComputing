package it.unical.dimes.reti.ese1.casa;

import java.io.Serializable;

public class Studente implements Serializable {
    private int matricola;
    private String nome, cognome, corsoDiLaurea;

    public Studente(int matricola, String nome, String cognome, String corsoDiLaurea) {
        this.matricola = matricola;
        this.nome = nome;
        this.cognome = cognome;
        this.corsoDiLaurea = corsoDiLaurea;
    }

    public int getMatricola() {
        return matricola;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getCorsoDiLaurea() {
        return corsoDiLaurea;
    }

    @Override
    public String toString() {
        return "Studente{" +
                "matricola=" + matricola +
                ", nome='" + nome + '\'' +
                ", cognome='" + cognome + '\'' +
                ", corsoDiLaurea='" + corsoDiLaurea + '\'' +
                '}';
    }
}
