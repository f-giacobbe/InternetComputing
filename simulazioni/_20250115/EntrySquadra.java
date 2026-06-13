package it.unical.dimes.reti.simulazioni._20250115;

import java.io.Serializable;

public class EntrySquadra implements Comparable<EntrySquadra>, Serializable {
    private String squadra;
    private int punti;
    private int goalFatti;
    private int goalSubiti;

    public EntrySquadra(String squadra, int punti, int goalFatti, int goalSubiti) {
        this.squadra = squadra;
        this.punti = punti;
        this.goalFatti = goalFatti;
        this.goalSubiti = goalSubiti;
    }

    public String getSquadra() {
        return squadra;
    }

    public int getPunti() {
        return punti;
    }

    public int getDiffReti() {
        return goalFatti - goalSubiti;
    }

    public int getGoalFatti() {
        return goalFatti;
    }

    public int getGoalSubiti() {
        return goalSubiti;
    }

    public void setSquadra(String squadra) {
        this.squadra = squadra;
    }

    public void setPunti(int punti) {
        this.punti = punti;
    }

    public void setGoalFatti(int goalFatti) {
        this.goalFatti = goalFatti;
    }

    public void setGoalSubiti(int goalSubiti) {
        this.goalSubiti = goalSubiti;
    }

    @Override
    public int compareTo(EntrySquadra es) {
        if (this.punti != es.getPunti()) {
            return Integer.compare(es.getPunti(), this.punti);
        }
        return Integer.compare(es.getDiffReti(), this.getDiffReti());
    }
}
