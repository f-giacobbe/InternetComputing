package it.unical.dimes.reti.simulazioni._20230404.es2;

import java.io.Serializable;

public class Asta implements Serializable {
    private int id;
    private String prodotto;

    public Asta(int id, String prodotto) {
        this.id = id;
        this.prodotto = prodotto;
    }

    public int getId() {
        return id;
    }

    public String getProdotto() {
        return prodotto;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Asta a) {
            return a.id == id;
        }
        return false;
    }
}
