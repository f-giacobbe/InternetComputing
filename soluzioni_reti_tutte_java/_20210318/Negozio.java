package it.unical.dimes.reti.soluzioni_reti_tutte_java._20210318;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class Negozio {
    private String idNegozio;
    private HashMap<String, Double> listino = new HashMap<String, Double>();

    public Negozio(String idNegozio) {
        this.idNegozio = idNegozio;
    }

    public synchronized void aggiungiProdotto(String idProdotto, double prezzoUnitario) {
        listino.put(idProdotto, prezzoUnitario);
    }

    public synchronized OffertaNegozio calcolaOfferta(RichiestaAcquisto richiesta) {
        if (!listino.containsKey(richiesta.getIdProdotto())) {
            return null;
        }
        double prezzoUnitario = listino.get(richiesta.getIdProdotto());
        double totale = prezzoUnitario * richiesta.getQuantita();
        return new OffertaNegozio(idNegozio, totale);
    }

    public void avvia() throws Exception {
        new ThreadRicezioneRiepilogoUDP().start();

        ServerSocket serverSocket = new ServerSocket(Server.PORTA_NEGOZI);
        System.out.println("Negozio " + idNegozio + " in ascolto sulla porta " + Server.PORTA_NEGOZI);

        while (true) {
            Socket socket = serverSocket.accept();
            new ThreadRichiestaNegozio(socket, this).start();
        }
    }

    public void gestisciRichiesta(Socket socket) {
        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            RichiestaAcquisto richiesta = (RichiestaAcquisto) in.readObject();
            OffertaNegozio offerta = calcolaOfferta(richiesta);

            if (offerta != null) {
                out.writeObject(offerta);
            } else {
                out.writeObject("Prodotto non disponibile");
            }
            out.flush();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
