package it.unical.dimes.reti.soluzioni_reti_tutte_java._20250115;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;

public class RicevitoreMulticast extends Thread {
    public void run() {
        try {
            MulticastSocket socket = new MulticastSocket(ServerTorneo.PORTA_MULTICAST);
            InetAddress gruppo = InetAddress.getByName(ServerTorneo.GRUPPO_MULTICAST);
            socket.joinGroup(gruppo);

            while (true) {
                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);
                String msg = new String(packet.getData(), 0, packet.getLength());
                System.out.println("[Multicast torneo] " + msg);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
