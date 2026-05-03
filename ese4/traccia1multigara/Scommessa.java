package it.unical.dimes.reti.ese4.traccia1multigara;

import java.net.InetAddress;

public class Scommessa {
    private static int prog = 0;

    private int id;
    private int gara;
    private int cavallo;
    private int importo;
    private InetAddress sorgente;

    public Scommessa(int gara, int cavallo, int importo, InetAddress sorgente) {
        this.gara = gara;
        this.cavallo = cavallo;
        this.importo = importo;
        this.sorgente = sorgente;

        this.id = prog++;
    }

    public int getId() {
        return id;
    }

    public int getGara() {
        return gara;
    }

    public int getCavallo() {
        return cavallo;
    }

    public int getImporto() {
        return importo;
    }

    public InetAddress getSorgente() {
        return sorgente;
    }
}
