package it.unical.dimes.reti.simulazioni._20230208.es2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;

public class Client {
    public int inviaDomanda(Partecipazione p) {
        try (Socket s = new Socket("localhost", Server.PORTA_TCP);
             ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
             BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()))) {
            out.writeObject(p);
            out.flush();

            return Integer.parseInt(in.readLine().split(" ")[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void attendiEsito() {
        try (MulticastSocket ms = new MulticastSocket(Server.PORTA_MULTICAST)) {
            ms.joinGroup(InetAddress.getByName(Server.IP_MULTICAST));
            byte[] buf = new byte[8192];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            ms.receive(packet);
            String data = new String(buf, 0, packet.getLength());
            System.out.println(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client c = new Client();
        Partecipazione p = new Partecipazione(1, "Francesco", "Giacobbe", "gcbfnc", "ingegnere informatico");
        int idProtocollo = c.inviaDomanda(p);
        c.attendiEsito();
    }
}
