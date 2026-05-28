package it.unical.dimes.reti.soluzioni_reti_tutte_java._20250115;
import java.io.Serializable;

public class Partita implements Serializable {
    private static final long serialVersionUID = 1L;

    private String codicePartita;
    private String squadraCasa;
    private String squadraOspite;
    private int golCasa;
    private int golOspite;
    private String dataOra;
    private long timestampInserimento;

    public Partita(String codicePartita, String squadraCasa, String squadraOspite,
                   int golCasa, int golOspite, String dataOra) {
        this.codicePartita = codicePartita;
        this.squadraCasa = squadraCasa;
        this.squadraOspite = squadraOspite;
        this.golCasa = golCasa;
        this.golOspite = golOspite;
        this.dataOra = dataOra;
        this.timestampInserimento = System.currentTimeMillis();
    }

    public String getCodicePartita() { return codicePartita; }
    public String getSquadraCasa() { return squadraCasa; }
    public String getSquadraOspite() { return squadraOspite; }
    public int getGolCasa() { return golCasa; }
    public int getGolOspite() { return golOspite; }
    public long getTimestampInserimento() { return timestampInserimento; }
}
