package it.unical.dimes.reti.soluzioni_reti_tutte_java._20230111_MIO;

import org.springframework.util.SerializationUtils;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class Server extends Thread {
    static final String IP_SERVER = "localhost";
    static final int PORTA_CLIENT = 3000;
    static final int PORTA_UDP = 4000;
    static final String MULTICAST = "230.0.0.1";
    static final int PORTA_MULTICAST = 5000;

    private final ConcurrentHashMap<String, Misura> misure = new ConcurrentHashMap<>();     // idSensore, Misura
    private final Set<String> sensoriKaput = new HashSet<>();

    @Override
    public void run() {
        new ThreadRaccoltaDatiSensore().start();
        new ThreadControlloSensori().start();
        new ThreadAscoltoClient().start();
    }





    private class ThreadAscoltoClient extends Thread {
        @Override
        public void run() {
            try (ServerSocket ss = new ServerSocket(PORTA_CLIENT)) {
                while (true) {
                    Socket s = ss.accept();
                    new ThreadRichiesteClient(s).start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }








    private class ThreadRichiesteClient extends Thread {
        private final Socket socket;

        ThreadRichiesteClient(Socket s) {
            socket = s;
        }

        @Override
        public void run() {
            try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream())) {
                String sensoreRichiesto = (String) in.readObject();

                out.writeObject(misure.get(sensoreRichiesto));
                out.flush();

                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }





    private class ThreadControlloSensori extends Thread {
        static final int DIECI_MINUTI_MS = 10 * 60 * 1000;

        @Override
        public void run() {
            try (MulticastSocket ms = new MulticastSocket()){
                while (true) {
                    TimeUnit.MINUTES.sleep(1);      // Faccio il controllo ogni minuto e non in continuazione per non intasare il server

                    Calendar adesso = Calendar.getInstance();
                    boolean almenoUnNuovoSensoreNonFunzionante = false;

                    for (Misura m : misure.values()) {
                        if (adesso.getTimeInMillis() - m.getTimestamp().getTimeInMillis() > DIECI_MINUTI_MS) {
                            sensoriKaput.add(m.getIdSensore());
                            almenoUnNuovoSensoreNonFunzionante = true;
                        }
                    }

                    if (almenoUnNuovoSensoreNonFunzionante) {
                        String messaggio = "Sensori non funzionanti: " + sensoriKaput;
                        byte[] buf = messaggio.getBytes();

                        DatagramPacket packet = new DatagramPacket(buf, buf.length, InetAddress.getByName(MULTICAST), PORTA_MULTICAST);
                        ms.send(packet);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }




    private class ThreadRaccoltaDatiSensore extends Thread {
        @Override
        public void run() {
            try (DatagramSocket ds = new DatagramSocket(PORTA_UDP)) {
                while (true) {
                    byte[] buf = new byte[8192];
                    DatagramPacket packet = new DatagramPacket(buf, buf.length);
                    ds.receive(packet);

                    Misura misura = (Misura) SerializationUtils.deserialize(Arrays.copyOf(buf, packet.getLength()));

                    String sensore = misura.getIdSensore();
                    misure.put(sensore, misura);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    public static void main(String[] args) {
        new Server().start();
    }
}
