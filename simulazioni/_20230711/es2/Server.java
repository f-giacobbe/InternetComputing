package it.unical.dimes.reti.simulazioni._20230711.es2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Server extends Thread {
    public static final int TCP_OFFERTA_NEGOZIO = 3000;
    public static final int TCP_RICHIESTA_CLIENT = 4000;
    public static final int UDP_MULTICAST = 5000;
    public static final int UDP_NOTIFICA = 6000;

    public static final String IP_MULTICAST = "230.0.0.1";

    private Map<String, Map<String, TreeSet<OffertaNegozio>>> offerteNazioni = new ConcurrentHashMap<>();    // Nazione, Prodotto, OffertaNegozio. Il treeset i ordina automaticamente per pezzo migliore
    private Map<ProdottoNazione, List<InetAddress>> clientNotifica = new ConcurrentHashMap<>();

    @Override
    public void run() {
        new AccettaOfferte().start();
        new AccettaRichieste().start();
    }

    private class AccettaOfferte extends Thread {
        @Override
        public void run() {
            try (ServerSocket ss = new ServerSocket(TCP_OFFERTA_NEGOZIO)) {
                while (true) {
                    new AccettaOfferta(ss.accept()).start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private class AccettaOfferta extends Thread {
            private Socket s;

            public AccettaOfferta(Socket s) {
                this.s = s;
            }

            @Override
            public void run() {
                try (ObjectInputStream in = new ObjectInputStream(s.getInputStream())) {
                    OffertaNegozio offerta = (OffertaNegozio) in.readObject();

                    if (offerta.getQuantita() == 0) {
                        cancellaOfferta(offerta);
                    } else {
                        boolean migliore = eMigliore(offerta);
                        inserisciOfferta(offerta);

                        if (migliore) {
                            inviaMulticast(offerta);
                            notificaClient(offerta);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private synchronized void cancellaOfferta(OffertaNegozio offerta) {
        TreeSet<OffertaNegozio> offerte = offerteNazioni.get(offerta.getNazione()).get(offerta.getProdotto());
        offerte.remove(offerta);
    }

    private boolean eMigliore(OffertaNegozio offerta) {
        Map<String, TreeSet<OffertaNegozio>> offerteNazione = offerteNazioni.get(offerta.getNazione());
        if (offerteNazione == null) {
            return true;
        }

        TreeSet<OffertaNegozio> offerte = offerteNazione.get(offerta.getProdotto());
        if (offerte == null) {
            return true;
        }

        return offerta.getPrezzo() < offerte.getFirst().getPrezzo();
    }

    private synchronized void inserisciOfferta(OffertaNegozio offerta) {
        String nazione = offerta.getNazione();
        String prodotto = offerta.getProdotto();

        if (!offerteNazioni.containsKey(nazione)) {
            offerteNazioni.put(nazione, new ConcurrentHashMap<>());
        }

        if (!offerteNazioni.get(nazione).containsKey(prodotto)) {
            offerteNazioni.get(nazione).put(prodotto, new TreeSet<>());
        }

        offerteNazioni.get(nazione).get(prodotto).add(offerta);
    }

    private void inviaMulticast(OffertaNegozio offerta) {
        try (MulticastSocket ms = new MulticastSocket()) {
            String messaggio = String.format("%s %f %s %s", offerta.getProdotto(), offerta.getPrezzo(), offerta.getPiva(), offerta.getNazione());
            byte[] buf = messaggio.getBytes();
            DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(IP_MULTICAST), UDP_MULTICAST);
            ms.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void notificaClient(OffertaNegozio offerta) {
        String messaggio = String.format("%s %f %s %s", offerta.getProdotto(), offerta.getPrezzo(), offerta.getPiva(), offerta.getNazione());
        byte[] buf = messaggio.getBytes();

        ProdottoNazione pn = new ProdottoNazione(offerta.getProdotto(), offerta.getNazione());
        List<InetAddress> client = clientNotifica.get(pn);

        try (DatagramSocket ds = new DatagramSocket()) {
            for (InetAddress inet : client) {
                DatagramPacket packet = new DatagramPacket(buf, buf.length, inet, UDP_NOTIFICA);
                ds.send(packet);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private class AccettaRichieste extends Thread {
        @Override
        public void run() {
            try (ServerSocket ss = new ServerSocket(TCP_RICHIESTA_CLIENT)) {
                while (true) {
                    new AccettaRichiesta(ss.accept()).start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private class AccettaRichiesta extends Thread {
            private Socket s;

            public AccettaRichiesta(Socket s) {
                this.s = s;
            }

            @Override
            public void run() {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream())) {
                    String richiesta = in.readLine();
                    String[] campi = richiesta.split(" ");

                    String prodotto = campi[0];
                    String nazione = campi[1];
                    boolean notifica = Boolean.parseBoolean(campi[2]);

                    OffertaNegozio migliore = miglioreOfferta(prodotto, nazione);

                    if (notifica) {
                        subscribeClient(s.getInetAddress(), prodotto, nazione);
                    }

                    if (migliore == null) {
                        out.writeObject("");
                    } else {
                        out.writeObject(migliore);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private OffertaNegozio miglioreOfferta(String prodotto, String nazione) {
        if (!offerteNazioni.containsKey(nazione) || !offerteNazioni.get(nazione).containsKey(prodotto)) {
            return null;
        }

        return offerteNazioni.get(nazione).get(prodotto).getFirst();
    }

    private synchronized void subscribeClient(InetAddress client, String prodotto, String nazione) {
        ProdottoNazione pn = new ProdottoNazione(prodotto, nazione);

        if (!clientNotifica.containsKey(pn)) {
            clientNotifica.put(pn, new LinkedList<>());
        }

        clientNotifica.get(pn).add(client);
    }

    public static void main(String[] args) {
        new Server().start();
    }
}
