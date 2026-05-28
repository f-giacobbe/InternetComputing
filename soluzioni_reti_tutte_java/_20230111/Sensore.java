package it.unical.dimes.reti.soluzioni_reti_tutte_java._20230111;
import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Random;

public class Sensore {
    private String idSensore;
    private String hostServer;

    public Sensore(String idSensore, String hostServer) {
        this.idSensore = idSensore;
        this.hostServer = hostServer;
    }

    public void inviaMisura(double valore) {
        try {
            Misura misura = new Misura(idSensore, valore, System.currentTimeMillis());

            ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(byteStream);
            out.writeObject(misura);
            out.flush();

            byte[] dati = byteStream.toByteArray();
            DatagramPacket packet = new DatagramPacket(dati, dati.length,
                    InetAddress.getByName(hostServer), Server.PORTA_UDP_SENSORI);

            DatagramSocket socket = new DatagramSocket();
            socket.send(packet);
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void avviaInvioPeriodico() {
        Random random = new Random();
        while (true) {
            double valore = 10 + random.nextDouble() * 40;
            inviaMisura(valore);
            try {
                Thread.sleep(5 * 60 * 1000);
            } catch (InterruptedException e) {
                return;
            }
        }
    }
}
