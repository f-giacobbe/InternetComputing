package it.unical.dimes.reti.soluzioni_reti_tutte_java._20240317;
import java.net.InetAddress;

public class Sottoscrizione {
    private String idSensore;
    private InetAddress indirizzo;

    public Sottoscrizione(String idSensore, InetAddress indirizzo) {
        this.idSensore = idSensore;
        this.indirizzo = indirizzo;
    }

    public String getIdSensore() { return idSensore; }
    public InetAddress getIndirizzo() { return indirizzo; }
}
