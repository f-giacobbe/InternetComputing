package it.unical.dimes.reti.simulazioni._20230711.es2;

import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;

public class Negozio {
    public static void main(String[] args) {
        OffertaNegozio offerta = new OffertaNegozio("123", "IT", "iphone", 799, 50);
        inviaOfferta(offerta);
        joinMulticast();
    }

    private static void inviaOfferta(OffertaNegozio offerta) {
        try (Socket s = new Socket("localhost", Server.TCP_OFFERTA_NEGOZIO);
        ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream())) {
            out.writeObject(offerta);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void joinMulticast() {
        try (MulticastSocket ms = new MulticastSocket(Server.UDP_MULTICAST)) {
            ms.joinGroup(InetAddress.getByName(Server.IP_MULTICAST));
            byte[] buf = new byte[512];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            ms.receive(packet);
            String messaggio = new String(buf, 0, packet.getLength());
            System.out.println(messaggio);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
