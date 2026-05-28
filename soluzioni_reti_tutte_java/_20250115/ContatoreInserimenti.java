package it.unical.dimes.reti.soluzioni_reti_tutte_java._20250115;
public class ContatoreInserimenti {
    private int attive = 0;
    private int massimo;

    public ContatoreInserimenti(int massimo) {
        this.massimo = massimo;
    }

    public synchronized boolean entraSePossibile() {
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
