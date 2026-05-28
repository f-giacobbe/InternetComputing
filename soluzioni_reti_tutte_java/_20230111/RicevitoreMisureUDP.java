package it.unical.dimes.reti.soluzioni_reti_tutte_java._20230111;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class RicevitoreMisureUDP extends Thread {
    private ArchivioMisure archivio;

    public RicevitoreMisureUDP(ArchivioMisure archivio) {
        this.archivio = archivio;
    }

    public void run() {
        try {
            DatagramSocket socket = new DatagramSocket(Server.PORTA_UDP_SENSORI);
            System.out.println("Ricevitore UDP avviato sulla porta " + Server.PORTA_UDP_SENSORI);

            while (true) {
                byte[] buffer = new byte[8192];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                ByteArrayInputStream byteStream = new ByteArrayInputStream(packet.getData(), 0, packet.getLength());
                ObjectInputStream in = new ObjectInputStream(byteStream);
                Misura misura = (Misura) in.readObject();

                archivio.salvaMisura(misura);
                System.out.println("Misura ricevuta: " + misura);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
