package it.unical.dimes.reti.soluzioni_reti_tutte_java._20250115;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerTorneo {
    public static final String HOSTNAME = "soccerleague.local";
    public static final int PORTA_TCP = 6666;
    public static final String GRUPPO_MULTICAST = "239.255.0.1";
    public static final int PORTA_MULTICAST = 5000;
    public static final long DIECI_MINUTI = 10 * 60 * 1000;

    private ArchivioTorneo archivio = new ArchivioTorneo();
    private ContatoreInserimenti contatoreInserimenti = new ContatoreInserimenti(5);

    public void avvia() throws IOException {
        new ThreadMulticastTorneo(archivio).start();

        ServerSocket serverSocket = new ServerSocket(PORTA_TCP);
        System.out.println("Server torneo avviato sulla porta " + PORTA_TCP);

        while (true) {
            Socket socket = serverSocket.accept();
            new ThreadRichiestaTorneo(socket, this).start();
        }
    }

    public void gestisciRichiesta(Socket socket) {
        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            RichiestaTorneo richiesta = (RichiestaTorneo) in.readObject();
            RispostaTorneo risposta;

            if (RichiestaTorneo.INSERISCI_RISULTATO.equals(richiesta.getOperazione())) {
                if (!contatoreInserimenti.entraSePossibile()) {
                    risposta = new RispostaTorneo(false, "Errore: server sovraccarico", null);
                } else {
                    try {
                        risposta = archivio.inserisciPartita(richiesta.getPartita());
                    } finally {
                        contatoreInserimenti.esci();
                    }
                }
            } else if (RichiestaTorneo.RICHIEDI_CLASSIFICA.equals(richiesta.getOperazione())) {
                risposta = new RispostaTorneo(true, "Classifica aggiornata", archivio.copiaClassifica());
            } else {
                risposta = new RispostaTorneo(false, "Errore: operazione non valida", null);
            }

            out.writeObject(risposta);
            out.flush();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
