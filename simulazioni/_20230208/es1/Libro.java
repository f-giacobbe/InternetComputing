package it.unical.dimes.reti.simulazioni._20230208.es1;

import java.io.Serializable;

public class Libro implements Serializable {
    private String ISBN;
    private String categoria;

    public Libro(String i, String c) {
        ISBN = i;
        categoria = c;
    }

    public String getISBN() {
        return ISBN;
    }

    public String getCategoria() {
        return categoria;
    }
}
