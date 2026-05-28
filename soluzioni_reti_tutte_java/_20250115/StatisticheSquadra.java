package it.unical.dimes.reti.soluzioni_reti_tutte_java._20250115;
import java.io.Serializable;

public class StatisticheSquadra implements Serializable {
    private static final long serialVersionUID = 1L;

    private String nomeSquadra;
    private int punti;
    private int golFatti;
    private int golSubiti;

    public StatisticheSquadra(String nomeSquadra) {
        this.nomeSquadra = nomeSquadra;
    }

    public void aggiorna(int fatti, int subiti, int puntiDaAggiungere) {
        golFatti += fatti;
        golSubiti += subiti;
        punti += puntiDaAggiungere;
    }

    public int getPunti() { return punti; }
    public int getGolFatti() { return golFatti; }
    public int getGolSubiti() { return golSubiti; }
    public int getDifferenzaReti() { return golFatti - golSubiti; }

    public StatisticheSquadra copia() {
        StatisticheSquadra s = new StatisticheSquadra(nomeSquadra);
        s.punti = punti;
        s.golFatti = golFatti;
        s.golSubiti = golSubiti;
        return s;
    }

    public String toString() {
        return nomeSquadra + " punti=" + punti + " diff=" + getDifferenzaReti() +
               " GF=" + golFatti + " GS=" + golSubiti;
    }
}
