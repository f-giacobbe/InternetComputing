package it.unical.dimes.reti.simulazioni._20241108;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Server extends Thread {
    public Server(String hostname) {
        this.hostname = hostname;
    }

    public static final int PORTA_TCP_RICHIESTA = 2222;
    public static final int PORTA_MULTICAST = 5000;
    public static final String IP_MULTICAST = "239.255.0.1";

    private static final int LIMITE = 10;
    private static final int MINUTI_NOTIFICA = 15;

    private static Random random = new Random();

    private List<String> valuteValide = List.of("EUR", "USD", "YEN", "GBP");    // per esempio
    private List<String> clientValidi = List.of("1", "2", "3");     // esempio
    private int count = 0;  // counter conversioni in contemporanea
    private int totaleConversioni = 0;  // counter conversioni in totale
    private double sommaTassi = 0;   // per la media
    private List<ConversioneRequest> archivio = new LinkedList<>();
    private String hostname;

    private double tasso() {
        return random.nextDouble(0.1, 5);
    }

    public String getHostname() {
        return hostname;
    }

    @Override
    public void run() {
        new ThreadAccettaConversioni().start();
        new ThreadNotifica().start();
    }

    private class ThreadAccettaConversioni extends Thread {
        @Override
        public void run() {
            try (ServerSocket ss = new ServerSocket(PORTA_TCP_RICHIESTA)) {
                while (true) {
                    new ThreadAccettaConversione(ss.accept()).start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private class ThreadAccettaConversione extends Thread {
            private Socket s;

            ThreadAccettaConversione(Socket s) {
                this.s = s;
            }

            @Override
            public void run() {
                try (ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(s.getInputStream())) {
                    ConversioneRequest request = (ConversioneRequest) in.readObject();

                    synchronized (Server.this) {
                        if (count >= LIMITE || !clientValidi.contains(request.getIdClient()) || !valuteValide.contains(request.getValuta1()) || !valuteValide.contains(request.getValuta2())) {
                            out.writeObject("-1");
                            return;
                        } else {
                            count++;
                        }
                    }

                    double tasso = tasso();
                    double importoConvertito = request.getImporto() * tasso;

                    ConversioneResponse response = new ConversioneResponse(importoConvertito, tasso);
                    out.writeObject(response);

                    synchronized (Server.this) {
                        totaleConversioni++;
                        sommaTassi += tasso;
                        count--;
                        archivio.add(request);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class ThreadNotifica extends Thread {
        @Override
        public void run() {
            try (MulticastSocket ms = new MulticastSocket()) {
                while (true) {
                    TimeUnit.MINUTES.sleep(MINUTI_NOTIFICA);

                    String messaggio = "Totale conversioni: " + totaleConversioni + "; Tasso medio: " + sommaTassi/totaleConversioni;
                    byte[] buf = messaggio.getBytes();
                    DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(IP_MULTICAST), PORTA_MULTICAST);
                    ms.send(packet);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server("currency.dimes.unical.it");
        server.start();
    }
}
