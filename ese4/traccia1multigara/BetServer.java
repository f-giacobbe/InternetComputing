package it.unical.dimes.reti.ese4.traccia1multigara;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.*;
import java.util.Calendar;
import java.util.HashMap;

public class BetServer {
    private HashMap<Integer, Scommessa> scommesse;
    private int cavalli;    // Suppongo che il numero di cavalli sia conosciuto a priori
    private HashMap<Integer, Gara> gare;
    private int port;
    private int multicastPort;
    private InetAddress multicast;

    public BetServer(InetAddress multicast, int port, int multicastPort, int cavalli) {
        this.port = port;
        this.cavalli = cavalli;
        this.multicastPort = multicastPort;

        scommesse = new HashMap<>();
        gare = new HashMap<>();

        new BetAccepter().start();
    }

    public void aggiungiGara(Gara g) {
        gare.put(g.getId(), g);
        new GaraTerminataThread(g).start();
    }

    private class BetAccepter extends Thread {
        private ServerSocket ss;

        @Override
        public void run() {
            try {
                while (true) {
                    Socket s = ss.accept();
                    BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
                    PrintWriter out = new PrintWriter(s.getOutputStream(), true);

                    String input = in.readLine();
                    String[] campi = input.split(" ");
                    int gara = Integer.parseInt(campi[0]);
                    int cavallo = Integer.parseInt(campi[1]);
                    int importo = Integer.parseInt(campi[2]);

                    // Input validation
                    if (!gare.containsKey(gara) || cavallo <= 0 || cavallo > cavalli || importo <= 0) {
                        out.println("Invalid input");
                        continue;
                    }

                    // Verifico se la gara accetta scommesse
                    Calendar inizioScommesse = gare.get(gara).getInizio();
                    inizioScommesse.add(Calendar.HOUR, 1);
                    Calendar fineScommesse = gare.get(gara).getInizio();

                    Calendar now = Calendar.getInstance();

                    if (now.before(inizioScommesse) || now.after(fineScommesse)) {
                        out.println("false");
                        continue;
                    }

                    Scommessa scommessa = new Scommessa(gara, cavallo, importo, s.getInetAddress());
                    scommesse.put(scommessa.getId(), scommessa);

                    out.println("true");

                    out.close();
                    in.close();
                    s.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private class GaraTerminataThread extends Thread {
        private Gara gara;

        public GaraTerminataThread(Gara gara) {
            this.gara = gara;
        }

        @Override
        public void run() {
            try {
                // Attendo la fine della gara
                long quantoMancaAllaFine = gara.getInizio().getTimeInMillis() - Calendar.getInstance().getTimeInMillis();
                if (quantoMancaAllaFine > 0) {
                    Thread.sleep(quantoMancaAllaFine);
                }

                int vincitore = (int) (Math.random() * cavalli) + 1;

                // Ottengo lista vincitori
                String output = "Vincitori gara " + gara.getId() + ":\n";
                for (Scommessa s : scommesse.values()) {
                    if (s.getCavallo() == vincitore) {
                        output = output + s.getSorgente() + s.getImporto()*10 + "\n";
                    }
                }

                // Comunico in multicast
                MulticastSocket s = new MulticastSocket();
                byte[] buf = output.getBytes();
                DatagramPacket packet = new DatagramPacket(buf, buf.length, multicast, multicastPort);
                s.send(packet);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void main(String[] args) {
        try {
            InetAddress multicast = InetAddress.getByName("230.0.0.1");
            int port = 8001;
            int multicastPort = 8002;
            int cavalli = 12;

            BetServer server = new BetServer(multicast, port, multicastPort, cavalli);
            Calendar tra1h = Calendar.getInstance();
            tra1h.add(Calendar.HOUR, 1);
            server.aggiungiGara(new Gara(tra1h));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
