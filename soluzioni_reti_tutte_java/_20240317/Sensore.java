package it.unical.dimes.reti.soluzioni_reti_tutte_java._20240317;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;

public class Sensore {
    private String idSensore;
    private String hostServer;

    public Sensore(String idSensore, String hostServer) {
        this.idSensore = idSensore;
        this.hostServer = hostServer;
    }

    public void registraNotifiche() {
        try {
            Socket socket = new Socket(hostServer, Server.PORTA_REGISTRAZIONE);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            out.writeObject(idSensore);
            out.flush();
            System.out.println(in.readObject());
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void inviaStato(double temperatura, double umidita) {
        try {
            Socket socket = new Socket(hostServer, Server.PORTA_STATO);
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            StatoSensore stato = new StatoSensore(idSensore, temperatura, umidita);
            out.writeObject(stato);
            out.flush();

            RispostaServer risposta = (RispostaServer) in.readObject();
            System.out.println("Risposta server: " + risposta);
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void avviaRicezioneNotifiche() {
        new ThreadRicezioneNotifiche().start();
    }
}
