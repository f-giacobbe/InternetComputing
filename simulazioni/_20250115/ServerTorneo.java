package it.unical.dimes.reti.simulazioni._20250115;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.*;
import java.util.concurrent.TimeUnit;

public class ServerTorneo extends Thread {
    public static final int PORTA_TCP = 6666;
    public static final int PORTA_MULTICAST = 5000;
    public static final String IP_SERVER = "soccerleague.local";
    public static final String IP_MULTICAST = "239.255.0.1";

    // Supponendo che il numero di punti sia uguale al numero di goal effettuati
    private Set<EntrySquadra> classifica = new TreeSet<>();
    private int countRichieste = 0;
    private LinkedList<Partita> partite = new LinkedList<>();

    private synchronized void updatePartite(Partita p) {
        partite.addFirst(p);
    }

    private synchronized void updateCount(int count) {
        this.countRichieste += count;
    }

    private boolean updateClassifica(Partita p) {
        int puntiCasa = 0;
        int puntiOspite = 0;

        if (p.getGoalCasa() == p.getGoalOspite()) {
            puntiCasa = 1;
            puntiOspite = 1;
        } else if (p.getGoalCasa() > p.getGoalOspite()) {
            puntiCasa = 3;
        } else {
            puntiOspite = 3;
        }

        EntrySquadra casa = null;
        EntrySquadra ospite = null;

        for (EntrySquadra es : classifica) {
            if (es.getSquadra().equals(p.getCasa())) {
                casa = es;
            } else if (es.getSquadra().equals(p.getOspite())) {
                ospite = es;
            }

            if (casa != null && ospite != null) {
                break;
            }
        }

        if (casa == null || ospite == null) {
            return false;
        }

        synchronized (this) {
            casa.setPunti(casa.getPunti() + puntiCasa);
            casa.setGoalFatti(casa.getGoalFatti() + puntiCasa);
            casa.setGoalSubiti(casa.getGoalSubiti() + puntiOspite);

            ospite.setPunti(ospite.getPunti() + puntiOspite);
            ospite.setGoalFatti(ospite.getGoalFatti() + puntiOspite);
            ospite.setGoalSubiti(ospite.getGoalSubiti() + puntiCasa);
        }

        return true;
    }

    @Override
    public void run() {
        new ThreadRichieste().start();
        new ThreadMulticast().start();
    }

    private class ThreadRichieste extends Thread {
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
                try (ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(s.getInputStream())) {
                    Richiesta richiesta = (Richiesta) in.readObject();
                    if (richiesta.getCodice().equals("INSERISCI_RISULTATO")) {
                        synchronized (ServerTorneo.this) {
                            if (countRichieste >= 5) {
                                out.writeObject(new Risposta("Errore sovraccarico server, riprova più tardi", null));
                                return;
                            }
                        }

                        updateCount(1);

                        if (!updateClassifica(richiesta.getPartita())) {
                            out.writeObject(new Risposta("Errore squadra non esistente", null));
                            return;
                        }

                        updatePartite(richiesta.getPartita());
                        out.writeObject(new Risposta("Risultato aggiunto con successo", null));
                        updateCount(-1);
                    } else if (richiesta.getCodice().equals("RICHIEDI_CLASSIFICA")) {
                        out.writeObject(new Risposta("OK", List.copyOf(classifica)));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class ThreadMulticast extends Thread {
        @Override
        public void run() {
            try (MulticastSocket ms = new MulticastSocket()) {
                while (true) {
                    TimeUnit.MINUTES.sleep(10);

                    String messaggio = getInfoPartite();
                    byte[] buf = messaggio.getBytes();
                    DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(IP_MULTICAST), PORTA_MULTICAST);
                    ms.send(packet);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getInfoPartite() {
        int totale = 0;
        int sommaGoal = 0;

        Calendar start = Calendar.getInstance();
        start.add(Calendar.MINUTE, -10);        // start è dieci minuti fa

        for (Partita p : partite) {
            if (p.getTimestamp().after(start)) {
                totale++;
                sommaGoal += p.getGoalCasa() + p.getGoalOspite();
            } else {
                break;  // Supponendo le partite siano memorizzate in ordine cronologico
            }
        }

        return "Totale partite: " + totale + "; media goal: " + sommaGoal/(float)totale;
    }

    public static void main(String[] args) {
        new ServerTorneo().start();
    }
}
