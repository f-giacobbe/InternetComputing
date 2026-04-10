package it.unical.dimes.reti.ese2.timeServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Date;

public class TimeServer {
    public static final int PORT = 3575;

    public static void main(String[] args) {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(PORT);

            while (true) {
                byte[] buf = new byte[256];

                // Riceve la richiesta
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);

                // Produce la risposta
                String dString = new Date().toString();
                buf = dString.getBytes();

                // Invia la risposta al client
                InetAddress address = packet.getAddress();
                int port = packet.getPort();
                packet = new DatagramPacket(buf, buf.length, address, port);
                socket.send(packet);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            if (socket != null) {
                socket.close();
            }
        }
    }
}
