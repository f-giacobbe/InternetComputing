package it.unical.dimes.reti.soluzioni_reti_tutte_java._20241108;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {
    private String idClient;
    private String hostServer;

    public Client(String idClient, String hostServer) {
        this.idClient = idClient;
        this.hostServer = hostServer;
    }

    public void inviaRichiesta(String valutaDa, String valutaA, double importo) {
        try {
            Socket socket = new Socket(hostServer, Server.PORTA_TCP);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            String richiesta = idClient + "#" + valutaDa + "#" + valutaA + "#" + importo;
            out.writeObject(richiesta);
            out.flush();

            RispostaConversione risposta = (RispostaConversione) in.readObject();
            System.out.println(risposta);
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void avviaRicezioneMulticast() {
        new RicevitoreMulticast().start();
    }
}
