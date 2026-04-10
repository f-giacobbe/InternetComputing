package it.unical.dimes.reti.ese2.timeServer.geo;

import it.unical.dimes.reti.ese2.timeServer.TimeServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Calendar;
import java.util.TimeZone;

public class TimeServerGeo {
    public static void main(String[] args) {
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket(TimeServer.PORT);

            while (true) {
                byte[] buf = new byte[256];

                // Riceve la richiesta
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String timeZone = new String(packet.getData(), 0, packet.getLength());

                // Produce la risposta
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone(timeZone));
                String dString = calendar.get(Calendar.HOUR) + ":" + calendar.get(Calendar.MINUTE);
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
