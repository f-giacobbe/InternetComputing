package it.unical.dimes.reti.soluzioni_reti_tutte_java._20241108;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.LinkedList;

public class ThreadStatisticheMulticast extends Thread {
    private ArchivioConversioni archivio;

    public ThreadStatisticheMulticast(ArchivioConversioni archivio) {
        this.archivio = archivio;
    }

    public void run() {
        try {
            MulticastSocket socket = new MulticastSocket();
            InetAddress gruppo = InetAddress.getByName(Server.GRUPPO_MULTICAST);

            while (true) {
                Thread.sleep(Server.QUINDICI_MINUTI);
                long limite = System.currentTimeMillis() - Server.QUINDICI_MINUTI;
                LinkedList<Conversione> recenti = archivio.getConversioniDopo(limite);

                double sommaTassi = 0;
                for (int i = 0; i < recenti.size(); i++) {
                    sommaTassi += recenti.get(i).getTasso();
                }
                double media = 0;
                if (recenti.size() > 0) {
                    media = sommaTassi / recenti.size();
                }

                String msg = "Conversioni ultimi 15 minuti=" + recenti.size() + "; tasso medio=" + media;
                byte[] buffer = msg.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, gruppo, Server.PORTA_MULTICAST);
                socket.send(packet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
