package it.unical.dimes.reti.soluzioni_reti_tutte_java._20210318;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.LinkedList;

public class ThreadRiepilogoVendite extends Thread {
    private LinkedList<InfoNegozio> negozi;
    private ArchivioVendite archivioVendite;

    public ThreadRiepilogoVendite(LinkedList<InfoNegozio> negozi, ArchivioVendite archivioVendite) {
        this.negozi = negozi;
        this.archivioVendite = archivioVendite;
    }

    public void run() {
        try {
            while (true) {
                Thread.sleep(Server.VENTIQUATTRO_ORE);
                DatagramSocket udp = new DatagramSocket();

                for (int i = 0; i < negozi.size(); i++) {
                    InfoNegozio info = negozi.get(i);
                    double totale = archivioVendite.getTotale(info.getIdNegozio());
                    String msg = info.getIdNegozio() + "#Totale vendite giornaliere=" + totale;
                    byte[] buffer = msg.getBytes();
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length,
                            InetAddress.getByName(info.getHost()), Server.PORTA_UDP_RIEPILOGO);
                    udp.send(packet);
                }

                udp.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
