package it.unical.dimes.reti.soluzioni_reti_tutte_java._20241108;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Random;

public class Server {
    public static final String HOSTNAME = "currency.dimes.unical.it";
    public static final int PORTA_TCP = 2222;
    public static final String GRUPPO_MULTICAST = "239.255.0.1";
    public static final int PORTA_MULTICAST = 5000;
    public static final long QUINDICI_MINUTI = 15 * 60 * 1000;

    private ArchivioConversioni archivio = new ArchivioConversioni();
    private ContatoreRichieste contatore = new ContatoreRichieste(10);
    private HashMap<String, Boolean> clientValidi = new HashMap<String, Boolean>();
    private HashMap<String, Boolean> valuteValide = new HashMap<String, Boolean>();
    private Random random = new Random();

    public Server() {
        clientValidi.put("Client01", true);
        clientValidi.put("Client02", true);
        clientValidi.put("Client03", true);

        valuteValide.put("EUR", true);
        valuteValide.put("USD", true);
        valuteValide.put("GBP", true);
        valuteValide.put("JPY", true);
        valuteValide.put("CHF", true);
    }

    public void avvia() throws IOException {
        new ThreadStatisticheMulticast(archivio).start();

        ServerSocket serverSocket = new ServerSocket(PORTA_TCP);
        System.out.println("Server valuta avviato sulla porta " + PORTA_TCP);

        while (true) {
            Socket socket = serverSocket.accept();
            new ThreadConversione(socket, this).start();
        }
    }

    public void gestisciRichiesta(Socket socket) {
        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            String richiesta = (String) in.readObject();
            String[] parti = richiesta.split("#");
            if (parti.length != 4) {
                out.writeObject(new RispostaConversione(false, "Errore: formato richiesta non valido", -1, -1));
                out.flush();
                socket.close();
                return;
            }

            String idClient = parti[0];
            String da = parti[1];
            String a = parti[2];
            double importo = Double.parseDouble(parti[3]);

            if (!clientValidi.containsKey(idClient) || !valuteValide.containsKey(da) || !valuteValide.containsKey(a)) {
                out.writeObject(new RispostaConversione(false, "Errore: ID client o valuta non validi", -1, -1));
                out.flush();
                socket.close();
                return;
            }

            if (!contatore.provaAdEntrare()) {
                out.writeObject(new RispostaConversione(false, "-1", -1, -1));
                out.flush();
                socket.close();
                return;
            }

            try {
                double tasso = 0.1 + random.nextDouble() * (5.0 - 0.1);
                double risultato = importo * tasso;
                Conversione conversione = new Conversione(idClient, da, a, importo, risultato, tasso);
                archivio.aggiungi(conversione);
                out.writeObject(new RispostaConversione(true, "Conversione effettuata", risultato, tasso));
                out.flush();
            } finally {
                contatore.esci();
            }

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
