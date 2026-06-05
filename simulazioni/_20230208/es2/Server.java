package it.unical.dimes.reti.simulazioni._20230208.es2;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Server extends Thread {
    public static final int PORTA_TCP = 3000;
    public static final int PORTA_UDP = 4000;
    public static final int PORTA_MULTICAST = 5000;
    public static final String IP_MULTICAST = "230.0.0.1";

    private ConcurrentHashMap<Integer, Partecipazione> partecipazioni;
    private ConcurrentHashMap<Integer, Concorso> concorsiAttivi;      // id, Concorso
    private int count = 0;

    public Server(ConcurrentHashMap<Integer, Partecipazione> partecipazioni, ConcurrentHashMap<Integer, Concorso> concorsiAttivi) {
        this.partecipazioni = partecipazioni;
        this.concorsiAttivi = concorsiAttivi;
    }

    @Override
    public void run() {
        new ThreadAccettaRichieste().start();
        new ThreadAccettaCancellazioni().start();

        for (Concorso c : concorsiAttivi.values()) {
            new ThreadScadenzaConcorso(c).start();
        }
    }

    private class ThreadAccettaRichieste extends Thread {
        @Override
        public void run() {
            try (ServerSocket ss = new ServerSocket(PORTA_TCP)) {
                while (true) {
                    Socket s = ss.accept();
                    new ThreadRichiesta(s).start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private class ThreadRichiesta extends Thread {
            private Socket s;

            public ThreadRichiesta(Socket s) {
                this.s = s;
            }

            @Override
            public void run() {
                try (ObjectInputStream in = new ObjectInputStream(s.getInputStream());
                PrintWriter out = new PrintWriter(s.getOutputStream(), true)) {
                    Partecipazione partecipazione = (Partecipazione) in.readObject();

                    String res = "NOT_ACCEPTED";

                    if (verificaPartecipazione(partecipazione)) {
                        int idProtocollo = salvaPartecipazione(partecipazione);

                        res = idProtocollo + " " + new Date();
                    }

                    out.println(res);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private boolean verificaPartecipazione(Partecipazione p) {
        return concorsiAttivi.containsKey(p.getIdConcorso()) && p.getNome() != null && p.getCognome() != null
                && p.getCf() != null && p.getCv() != null && Calendar.getInstance().before(concorsiAttivi.get(p.getIdConcorso()).getScadenza());
    }

    private int salvaPartecipazione(Partecipazione p) {
        int id = count++;
        partecipazioni.put(id, p);
        return id;
    }

    private class ThreadAccettaCancellazioni extends Thread {
        @Override
        public void run() {
            try (DatagramSocket ds = new DatagramSocket(PORTA_UDP)) {
                while (true) {
                    byte[] buf = new byte[256];
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    ds.receive(packet);

                    int id = Integer.parseInt(new String(buf, 0, packet.getLength()));

                    String res = "false";
                    if (partecipazioni.remove(id) != null) {
                        res = "true";
                    }

                    buf = res.getBytes();
                    packet = new DatagramPacket(buf, buf.length, packet.getAddress(), packet.getPort());
                    ds.send(packet);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class ThreadScadenzaConcorso extends Thread {
        private Concorso concorso;

        public ThreadScadenzaConcorso(Concorso c) {
            concorso = c;
        }

        @Override
        public void run() {
            try (MulticastSocket ms = new MulticastSocket()) {
                Thread.sleep(concorso.getScadenza().getTimeInMillis() - Calendar.getInstance().getTimeInMillis());
                // Concorso scaduto
                concorsiAttivi.remove(concorso.getId());

                String risultato = concorso.getId() + "\n";

                LinkedList<String> partecipanti = new LinkedList<>();
                for (Partecipazione p : partecipazioni.values()) {
                    if (p.getIdConcorso() == concorso.getId()) {
                        partecipanti.add(p.getCf());
                    }
                }
                for (int i = 0; i < concorso.getPosti(); i++) {
                    String vincitore = partecipanti.remove(new Random().nextInt(partecipanti.size()));
                    risultato += vincitore + "\n";
                }


                byte[] buf = risultato.getBytes();
                DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(IP_MULTICAST), PORTA_MULTICAST);
                ms.send(packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Concorso c1 = new Concorso(1, 30, new Calendar.Builder().setDate(2026, 8, 30).build());
        Concorso c2 = new Concorso(2, 50, new Calendar.Builder().setDate(2026, 6, 30).build());

        ConcurrentHashMap<Integer, Concorso> concorsi = new ConcurrentHashMap<>();
        concorsi.put(c1.getId(), c1);
        concorsi.put(c2.getId(), c2);

        new Server(new ConcurrentHashMap<>(), concorsi).start();
    }
}
