package it.unical.dimes.reti.soluzioni_reti_tutte_java._20250115;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.util.LinkedList;

public class ThreadMulticastTorneo extends Thread {
    private ArchivioTorneo archivio;

    public ThreadMulticastTorneo(ArchivioTorneo archivio) {
        this.archivio = archivio;
    }

    public void run() {
        try {
            MulticastSocket socket = new MulticastSocket();
            InetAddress gruppo = InetAddress.getByName(ServerTorneo.GRUPPO_MULTICAST);

            while (true) {
                Thread.sleep(ServerTorneo.DIECI_MINUTI);
                long limite = System.currentTimeMillis() - ServerTorneo.DIECI_MINUTI;
                LinkedList<Partita> recenti = archivio.getPartiteDopo(limite);

                int totaleGol = 0;
                for (int i = 0; i < recenti.size(); i++) {
                    totaleGol += recenti.get(i).getGolCasa() + recenti.get(i).getGolOspite();
                }
                double media = 0;
                if (recenti.size() > 0) {
                    media = (double) totaleGol / recenti.size();
                }

                String msg = "Partite ultimi 10 minuti=" + recenti.size() + "; media gol=" + media;
                byte[] buffer = msg.getBytes();
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, gruppo, ServerTorneo.PORTA_MULTICAST);
                socket.send(packet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
