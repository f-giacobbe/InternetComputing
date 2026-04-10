package it.unical.dimes.reti.ese2.timeServer.geo;

import it.unical.dimes.reti.ese2.timeServer.TimeServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class TimeClientGeo {
    public static void main(String[] args) {
        DatagramSocket socket = null;
        try {
            String hostname = "localhost";
            socket = new DatagramSocket();

            // L'utente sceglie la time zone
            BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
            System.out.print("Time Zone: ");
            String timeZone = sc.readLine();

            // Invia la richiesta
            /*
            ATTENZIONE: esempi di time zone valide sono:
                Europe/Rome
                America/Los_Angeles
                Asia/Tokyo
             */
            byte[] buf = timeZone.getBytes();
            InetAddress address = InetAddress.getByName(hostname);
            DatagramPacket packet = new DatagramPacket(buf, buf.length, address, TimeServer.PORT);
            socket.send(packet);

            // Riceve la risposta
            buf = new byte[256];
            packet = new DatagramPacket(buf, buf.length);
            socket.receive(packet);

            // Visualizza la risposta
            String received = new String(packet.getData(), 0, packet.getLength());
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
