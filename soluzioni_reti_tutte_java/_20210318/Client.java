package it.unical.dimes.reti.soluzioni_reti_tutte_java._20210318;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private String hostServer;

    public Client(String hostServer) {
        this.hostServer = hostServer;
    }

    public void inviaRichiesta(String idProdotto, int quantita, double prezzoMassimo) {
        try {
            Socket socket = new Socket(hostServer, Server.PORTA_CLIENT);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            RichiestaAcquisto richiesta = new RichiestaAcquisto(idProdotto, quantita, prezzoMassimo);
            out.writeObject(richiesta);
            out.flush();

            Object risposta = in.readObject();
            System.out.println("Risposta server: " + risposta);
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
