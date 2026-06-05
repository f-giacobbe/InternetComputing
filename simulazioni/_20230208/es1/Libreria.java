package it.unical.dimes.reti.simulazioni._20230208.es1;

import java.io.Serializable;

public class Libreria implements Serializable {
    private String partitaIva;
    private String nome;
    private String citta;

    public Libreria(String p, String n, String c) {
        partitaIva = p;
        nome = n;
        citta = c;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public String getNome() {
        return nome;
    }

    public String getCitta() {
        return citta;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Libreria l && l.getPartitaIva().equals(partitaIva);
    }
}
