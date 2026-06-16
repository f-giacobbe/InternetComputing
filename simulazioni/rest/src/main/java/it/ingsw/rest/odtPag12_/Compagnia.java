package it.ingsw.rest.odtPag12_;

import jakarta.persistence.Id;

import java.util.List;

public class Compagnia {
    @Id
    private int codice;
    private String nome;
    private String sede;
    private List<Polizza> polizze;

    public Compagnia(int codice, String nome, String sede, List<Polizza> polizze) {
        this.codice = codice;
        this.nome = nome;
        this.sede = sede;
        this.polizze = polizze;
    }

    public int getCodice() {
        return codice;
    }

    public String getNome() {
        return nome;
    }

    public String getSede() {
        return sede;
    }

    public List<Polizza> getPolizze() {
        return polizze;
    }
}
