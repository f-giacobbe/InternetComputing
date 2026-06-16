package it.unical.dimes.reti.simulazioni._20241108;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;

public class Client {
    private String id;

    public Client(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    // Object può essere ConversioneResponse oppure String in caso di errore
    public Object inviaRichiestaConversione(ConversioneRequest request) {
        try (Socket s = new Socket("currency.dimes.unical.it", Server.PORTA_TCP_RICHIESTA);
        ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(s.getInputStream())) {
            out.writeObject(request);
            out.flush();

            return in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void ascoltaNotifiche() {
        try (MulticastSocket ms = new MulticastSocket(Server.PORTA_MULTICAST)) {
            ms.joinGroup(InetAddress.getByName(Server.IP_MULTICAST));

            while (true) {
                byte[] buf = new byte[512];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                ms.receive(packet);
                String messaggio = new String(buf, 0, packet.getLength());
                System.out.println(messaggio);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Client client = new Client("1");

        ConversioneRequest r1 = new ConversioneRequest(client.getId(), "EUR", "USD", 3000.0);
        ConversioneRequest r2 = new ConversioneRequest(client.getId(), "YEN", "GBP", 55000.0);

        for (ConversioneRequest r : new ConversioneRequest[]{r1, r2}) {
            Object response = client.inviaRichiestaConversione(r);

            if (response instanceof ConversioneResponse cr) {
                System.out.println(cr.getImporto() + " " + cr.getTasso());
            } else {
                System.out.println(response);
            }
        }

        client.ascoltaNotifiche();
    }
}
