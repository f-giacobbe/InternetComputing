package it.unical.dimes.reti.soluzioni_reti_tutte_java._20250115;
import java.io.Serializable;
import java.util.HashMap;

public class RispostaTorneo implements Serializable {
    private static final long serialVersionUID = 1L;

    private boolean ok;
    private String messaggio;
    private HashMap<String, StatisticheSquadra> classifica;

    public RispostaTorneo(boolean ok, String messaggio, HashMap<String, StatisticheSquadra> classifica) {
        this.ok = ok;
        this.messaggio = messaggio;
        this.classifica = classifica;
    }

    public boolean isOk() { return ok; }
    public String getMessaggio() { return messaggio; }
    public HashMap<String, StatisticheSquadra> getClassifica() { return classifica; }

    public String toString() {
        return messaggio + " " + (classifica != null ? classifica.toString() : "");
    }
}
