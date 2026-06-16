package it.unical.dimes.reti.simulazioni._20241108;

import java.io.Serializable;

public class ConversioneResponse implements Serializable {
    private double importo;
    private double tasso;

    public ConversioneResponse(double importo, double tasso) {
        this.importo = importo;
        this.tasso = tasso;
    }

    public double getImporto() {
        return importo;
    }

    public double getTasso() {
        return tasso;
    }
}
