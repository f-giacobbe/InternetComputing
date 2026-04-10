package it.unical.dimes.reti.ese2.timeServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class TimeClient {
    public static void main(String[] args) {
        DatagramSocket socket = null;
        try {
            String hostname = "localhost";
            socket = new DatagramSocket();

            // Invia la richiesta
            byte[] buf = new byte[256];
            InetAddress address = InetAddress.getByName(hostname);
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, TimeServer.PORT);
            socket.send(packet);

            // Riceve la risposta
            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);

            // Visualizza la risposta
            String received = new String(packet.getData());
            System.out.printf("Response: %s%n", received);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}
