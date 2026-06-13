package it.unical.dimes.reti.simulazioni._20250115;

import java.io.Serializable;
import java.util.List;

public class Risposta implements Serializable {
    private String messaggio;
    private List<EntrySquadra> classifica;

    public Risposta(String messaggio, List<EntrySquadra> classifica) {
        this.messaggio = messaggio;
        this.classifica = classifica;
    }

    public String getMessaggio() {
        return messaggio;
    }

    public List<EntrySquadra> getClassifica() {
        return classifica;
    }
}
