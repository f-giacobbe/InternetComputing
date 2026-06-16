package it.unical.dimes.reti.simulazioni._20241108;

import java.io.Serializable;

public class ConversioneRequest implements Serializable {
    private String idClient;
    private String valuta1;
    private String valuta2;
    private double importo;

    public ConversioneRequest(String idClient, String valuta1, String valuta2, double importo) {
        this.idClient = idClient;
        this.valuta1 = valuta1;
        this.valuta2 = valuta2;
        this.importo = importo;
    }

    public String getIdClient() {
        return idClient;
    }

    public String getValuta1() {
        return valuta1;
    }

    public String getValuta2() {
        return valuta2;
    }

    public double getImporto() {
        return importo;
    }
}
