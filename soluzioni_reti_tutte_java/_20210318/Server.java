package it.unical.dimes.reti.soluzioni_reti_tutte_java._20210318;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server {
    public static final String HOSTNAME = "shop.dimes.it";
    public static final int PORTA_CLIENT = 1111;
    public static final int PORTA_NEGOZI = 2222;
    public static final int PORTA_UDP_RIEPILOGO = 3333;
    public static final long VENTIQUATTRO_ORE = 24 * 60 * 60 * 1000;

    private LinkedList<InfoNegozio> negozi = new LinkedList<InfoNegozio>();
    private ArchivioVendite archivioVendite = new ArchivioVendite();

    public Server() {
        negozi.add(new InfoNegozio("Negozio1", "localhost"));
    }

    public void avvia() throws IOException {
        new ThreadRiepilogoVendite(negozi, archivioVendite).start();

        ServerSocket serverSocket = new ServerSocket(PORTA_CLIENT);
        System.out.println("Server acquisti avviato sulla porta " + PORTA_CLIENT);

        while (true) {
            Socket clientSocket = serverSocket.accept();
            new ThreadRichiestaClient(clientSocket, this).start();
        }
    }

    public void gestisciRichiestaClient(Socket clientSocket) {
        try {
            ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(clientSocket.getOutputStream());

            RichiestaAcquisto richiesta = (RichiestaAcquisto) in.readObject();
            OffertaNegozio migliore = null;

            for (int i = 0; i < negozi.size(); i++) {
                InfoNegozio info = negozi.get(i);
                OffertaNegozio offerta = richiediOffertaAlNegozio(info, richiesta);

                if (offerta != null && offerta.getPrezzoAcquisto() <= richiesta.getPrezzoMassimo()) {
                    if (migliore == null || offerta.getPrezzoAcquisto() < migliore.getPrezzoAcquisto()) {
                        migliore = offerta;
                    }
                }
            }

            if (migliore != null) {
                archivioVendite.aggiungiVendita(migliore.getIdNegozio(), migliore.getPrezzoAcquisto());
                out.writeObject(migliore);
            } else {
                out.writeObject("Nessuna offerta e arrivata");
            }
            out.flush();
            clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private OffertaNegozio richiediOffertaAlNegozio(InfoNegozio info, RichiestaAcquisto richiesta) {
        try {
            Socket socket = new Socket(info.getHost(), PORTA_NEGOZI);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            out.writeObject(richiesta);
            out.flush();
            Object risposta = in.readObject();
            socket.close();

            if (risposta instanceof OffertaNegozio) {
                return (OffertaNegozio) risposta;
            }
        } catch (Exception e) {
            System.out.println("Negozio non raggiungibile: " + info.getIdNegozio());
        }
        return null;
    }
}
