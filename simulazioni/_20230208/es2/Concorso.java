package it.unical.dimes.reti.simulazioni._20230208.es2;

import java.io.Serializable;
import java.util.Calendar;

public class Concorso {
    private int id;
    private int posti;
    private Calendar scadenza;

    public Concorso(int id, int posti, Calendar scadenza) {
        this.id = id;
        this.posti = posti;
        this.scadenza = scadenza;
    }

    public int getId() {
        return id;
    }

    public int getPosti() {
        return posti;
    }

    public Calendar getScadenza() {
        return (Calendar) scadenza.clone();
    }
}
