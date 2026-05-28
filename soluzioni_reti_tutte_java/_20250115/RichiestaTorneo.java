package it.unical.dimes.reti.soluzioni_reti_tutte_java._20250115;
import java.io.Serializable;

public class RichiestaTorneo implements Serializable {
    private static final long serialVersionUID = 1L;

    public static final String INSERISCI_RISULTATO = "INSERISCI_RISULTATO";
    public static final String RICHIEDI_CLASSIFICA = "RICHIEDI_CLASSIFICA";

    private String idClient;
    private String operazione;
    private Partita partita;

    public RichiestaTorneo(String idClient, String operazione, Partita partita) {
        this.idClient = idClient;
        this.operazione = operazione;
        this.partita = partita;
    }

    public String getIdClient() { return idClient; }
    public String getOperazione() { return operazione; }
    public Partita getPartita() { return partita; }
}
