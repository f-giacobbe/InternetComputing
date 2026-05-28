package it.unical.dimes.reti.soluzioni_reti_tutte_java._20250115;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientTorneo {
    private String idClient;
    private String hostServer;

    public ClientTorneo(String idClient, String hostServer) {
        this.idClient = idClient;
        this.hostServer = hostServer;
    }

    public RispostaTorneo inserisciRisultato(String codice, String casa, String ospite,
                                             int golCasa, int golOspite, String dataOra) {
        Partita partita = new Partita(codice, casa, ospite, golCasa, golOspite, dataOra);
        RichiestaTorneo richiesta = new RichiestaTorneo(idClient, RichiestaTorneo.INSERISCI_RISULTATO, partita);
        return inviaRichiesta(richiesta);
    }

    public RispostaTorneo richiediClassifica() {
        RichiestaTorneo richiesta = new RichiestaTorneo(idClient, RichiestaTorneo.RICHIEDI_CLASSIFICA, null);
        return inviaRichiesta(richiesta);
    }

    private RispostaTorneo inviaRichiesta(RichiestaTorneo richiesta) {
        try {
            Socket socket = new Socket(hostServer, ServerTorneo.PORTA_TCP);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            out.writeObject(richiesta);
            out.flush();
            RispostaTorneo risposta = (RispostaTorneo) in.readObject();
            socket.close();
            return risposta;
        } catch (Exception e) {
            e.printStackTrace();
            return new RispostaTorneo(false, "Errore di comunicazione", null);
        }
    }

    public void avviaRicezioneMulticast() {
        new RicevitoreMulticast().start();
    }
}
