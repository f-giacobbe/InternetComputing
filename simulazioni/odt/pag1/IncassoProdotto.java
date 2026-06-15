package it.unical.dimes.reti.simulazioni.odt.pag1;

import java.io.Serializable;

public class IncassoProdotto implements Serializable {
    private String tipoMarmellata;
    private double importo;

    public IncassoProdotto(String tipoMarmellata, double importo) {
        this.tipoMarmellata = tipoMarmellata;
        this.importo = importo;
    }

    public String getTipoMarmellata() {
        return tipoMarmellata;
    }

    public double getImporto() {
        return importo;
    }
}
