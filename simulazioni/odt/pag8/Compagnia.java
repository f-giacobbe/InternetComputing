package it.unical.dimes.reti.simulazioni.odt.pag8;

import java.io.Serializable;
import java.util.List;

public class Compagnia implements Serializable {
    private int codice;
    private String nome;
    private String sede;
    private List<Polizza> listPol;

    public Compagnia(int codice, String nome, String sede, List<Polizza> listPol) {
        this.codice = codice;
        this.nome = nome;
        this.sede = sede;
        this.listPol = listPol;
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

    public List<Polizza> getListPol() {
        return listPol;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Compagnia c) {
            return codice == c.getCodice();
        }
        return false;
    }
}
