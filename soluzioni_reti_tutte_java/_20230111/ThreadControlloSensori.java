package it.unical.dimes.reti.soluzioni_reti_tutte_java._20230111;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.LinkedList;

public class ThreadControlloSensori extends Thread {
    private ArchivioMisure archivio;

    public ThreadControlloSensori(ArchivioMisure archivio) {
        this.archivio = archivio;
    }

    public void run() {
        try {
            MulticastSocket socket = new MulticastSocket();
            InetAddress gruppo = InetAddress.getByName(Server.GRUPPO_MULTICAST);

            while (true) {
                Thread.sleep(60000);
                LinkedList<String> guasti = archivio.getSensoriNonFunzionanti(Server.DIECI_MINUTI);

                if (guasti.size() > 0) {
                    String messaggio = "Sensori non funzionanti: " + guasti.toString();
                    byte[] buffer = messaggio.getBytes();
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length, gruppo, Server.PORTA_MULTICAST);
                    socket.send(packet);
                    System.out.println("Multicast inviato: " + messaggio);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
