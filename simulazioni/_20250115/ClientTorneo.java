package it.unical.dimes.reti.simulazioni._20250115;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.List;

public class ClientTorneo {
    private int id;

    public ClientTorneo(int id) {
        this.id = id;
    }

    private String inserisciRisultato(Partita p) {
        try (Socket s = new Socket(ServerTorneo.IP_SERVER, ServerTorneo.PORTA_TCP);
         ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
         ObjectInputStream in = new ObjectInputStream(s.getInputStream())) {
            out.writeObject(new Richiesta(id, "INSERISCI_RISULTATO", p));
            out.flush();

            Risposta risposta = (Risposta) in.readObject();
            return risposta.getMessaggio();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private List<EntrySquadra> richiediClassifica() {
        try (Socket s = new Socket(ServerTorneo.IP_SERVER, ServerTorneo.PORTA_TCP);
        ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(s.getInputStream())) {
            out.writeObject(new Richiesta(id, "RICHIEDI_CLASSIFICA", null));
            out.flush();

            Risposta risposta = (Risposta) in.readObject();
            return risposta.getClassifica();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private class ThreadMulticast extends Thread {
        @Override
        public void run() {
            try (MulticastSocket ms = new MulticastSocket(ServerTorneo.PORTA_MULTICAST)) {
                ms.joinGroup(InetAddress.getByName(ServerTorneo.IP_MULTICAST));

                while (true) {
                    byte[] buf = new byte[256];
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    ms.receive(packet);

                    String messaggio = new String(buf, 0, packet.getLength());
                    System.out.println(messaggio);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        ClientTorneo c = new ClientTorneo(1);

        c.new ThreadMulticast().start();

        System.out.println(c.inserisciRisultato(new Partita("3", "Juventus", "Inter", 0, 3, Calendar.getInstance())));
        System.out.println(c.richiediClassifica());
    }
}
