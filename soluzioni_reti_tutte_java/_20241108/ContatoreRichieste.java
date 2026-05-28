package it.unical.dimes.reti.soluzioni_reti_tutte_java._20241108;
public class ContatoreRichieste {
    private int attive = 0;
    private int massimo;

    public ContatoreRichieste(int massimo) {
        this.massimo = massimo;
    }

    public synchronized boolean provaAdEntrare() {
        if (attive >= massimo) {
            return false;
        }
        attive++;
        return true;
    }

    public synchronized void esci() {
        if (attive > 0) {
            attive--;
        }
    }
}
