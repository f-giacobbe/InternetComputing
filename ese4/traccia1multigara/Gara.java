package it.unical.dimes.reti.ese4.traccia1multigara;

import java.util.Calendar;

public class Gara {
    private static int prog = 0;
    private int id;
    private Calendar inizio;

    public Gara(Calendar inizio) {
        this.id = prog++;
        this.inizio = inizio;
    }

    public Calendar getInizio() {
        return inizio;
    }

    public int getId() {
        return id;
    }
}
