package it.unical.dimes.reti.simulazioni._20250115;

import java.io.Serializable;
import java.util.Calendar;

public class Partita implements Serializable {
    private String id;
    private String casa;
    private String ospite;
    private int goalCasa;
    private int goalOspite;
    private Calendar timestamp;

    public Partita(String id, String casa, String ospite, int goalCasa, int goalOspite, Calendar timestamp) {
        this.id = id;
        this.casa = casa;
        this.ospite = ospite;
        this.goalCasa = goalCasa;
        this.goalOspite = goalOspite;
        this.timestamp = timestamp;
    }

    public String getId() {
        return id;
    }

    public String getCasa() {
        return casa;
    }

    public String getOspite() {
        return ospite;
    }

    public int getGoalCasa() {
        return goalCasa;
    }

    public int getGoalOspite() {
        return goalOspite;
    }

    public Calendar getTimestamp() {
        return timestamp;
    }
}
