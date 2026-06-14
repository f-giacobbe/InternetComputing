package it.unical.dimes.reti.simulazioni._20230404.es2;

import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.HashMap;
import java.util.Random;
import java.util.TreeSet;

public class Server {
    private static final Random random = new Random();

    public static final String IP = "localhost";
    public static final String IP_MULTICAST = "230.0.0.1";
    public static final int PORTA_MULTICAST = 5000;
    public static final int PORTA_UDP = 4000;
    public static final int START_P = 30000;
    public static final int END_P = 40000;

    public static final int UN_ORA = 60 * 60 * 1000;

    public void mettiAllAsta(Asta asta) {
        new ThreadAsta(asta).start();
    }

    private class ThreadAsta extends Thread {
        private Asta asta;
        private int P;
        private TreeSet<Offerta> offerte = new TreeSet<>();     // ordinato per importo più alto
        private HashMap<Offerta, InetAddress> indirizzi = new HashMap<>();
        private boolean attiva = true;

        ThreadAsta(Asta asta) {
            this.asta = asta;
        }

        private synchronized void aggiungiOfferta(Offerta o, InetAddress addr) {
            offerte.add(o);
            indirizzi.put(o, addr);
        }

        @Override
        public void run() {
            P = scegliP();
            inviaNotificaAsta();
            new ThreadAccettaOfferte().start();
        }

        private int scegliP() {
            return random.nextInt(START_P, END_P);
        }

        private void inviaNotificaAsta() {
            try (MulticastSocket ms = new MulticastSocket()) {
                String mess = asta.getId() + " " + P + " " + asta.getProdotto();
                byte[] buf = mess.getBytes();

                DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(IP_MULTICAST), PORTA_MULTICAST);
                ms.send(packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private class ThreadAccettaOfferte extends Thread {
            @Override
            public void run() {
                try (ServerSocket ss = new ServerSocket(P)) {
                    ss.setSoTimeout(UN_ORA);

                    while (attiva) {
                        Socket s = ss.accept();
                        new ThreadAccettaOfferta(s).start();
                    }
                } catch (SocketTimeoutException e) {
                    attiva = false;
                    new ThreadRifiutaOfferte().start();
                    esito();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private class ThreadAccettaOfferta extends Thread {
                private Socket s;

                ThreadAccettaOfferta(Socket s) {
                    this.s = s;
                }

                @Override
                public void run() {
                    try (PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                    ObjectInputStream in = new ObjectInputStream(s.getInputStream())) {
                        Offerta offerta = (Offerta) in.readObject();
                        aggiungiOfferta(offerta, s.getInetAddress());
                        out.println("true");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        private class ThreadRifiutaOfferte extends Thread {
            @Override
            public void run() {
                try (ServerSocket ss = new ServerSocket(P)) {
                    while (!attiva) {
                        Socket s = ss.accept();
                        new ThreadRifiutaOfferta(s).start();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private class ThreadRifiutaOfferta extends Thread {
                private Socket s;

                ThreadRifiutaOfferta(Socket s) {
                    this.s = s;
                }

                @Override
                public void run() {
                    try (PrintWriter out = new PrintWriter(s.getOutputStream(), true)) {
                        out.println("false");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        private void esito() {
            Offerta vincente = offerte.getFirst();
            InetAddress vincitore = indirizzi.get(vincente);

            try (DatagramSocket ds = new DatagramSocket()) {
                String mess = "Complimenti! Hai vinto l'asta " + asta.getId();
                byte[] buf = mess.getBytes();

                DatagramPacket packet = new DatagramPacket(buf, buf.length, vincitore, PORTA_UDP);
                ds.send(packet);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
        Asta a1 = new Asta(1, "iPhone");
        Asta a2 = new Asta(2, "Samsung Galaxy");
        server.mettiAllAsta(a1);
        server.mettiAllAsta(a2);
    }
}
