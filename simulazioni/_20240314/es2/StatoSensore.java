package it.unical.dimes.reti.simulazioni._20240314.es2;

import java.io.Serializable;

public class StatoSensore implements Serializable {
    private String id;
    private int prog;
    private double temperatura;
    private double umidita;

    public StatoSensore(String id, int prog, double temperatura, double umidita) {
        this.id = id;
        this.prog = prog;
        this.temperatura = temperatura;
        this.umidita = umidita;
    }

    public String getId() {
        return id;
    }

    public int getProg() {
        return prog;
    }

    public double getTemperatura() {
        return temperatura;
    }

    public double getUmidita() {
        return umidita;
    }

    public void setProg(int prog) {
        this.prog = prog;
    }
}
