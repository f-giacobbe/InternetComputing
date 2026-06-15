package it.unical.dimes.reti.simulazioni._20240314.es2;

import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Server extends Thread {
    public static final String HOSTNAME = "agricoltura.dimes.unical.it";
    public static final int PORTA_RICEZIONE_TCP = 3000;
    public static final int PORTA_RICHIESTA_NOTIFICA_TCP = 4000;
    public static final int PORTA_RICEZIONE_NOTIFICA_UDP = 4000;

    private static final int ORA_INIZIO = 8;
    private static final int ORA_FINE = 13;
    private static final double EPS_N = 0.95;
    private static final double EPS_P = 1.05;

    private int prog = 0;
    private List<InetAddress> indirizziNotifica = new LinkedList<>();
    private HashMap<String, List<StatoSensore>> archivio = new HashMap<>();    // idSensore, lista stati inviati

    @Override
    public void run() {
        new ThreadStati().start();
        new ThreadRichiestaNotifiche().start();
    }

    private class ThreadStati extends Thread {
        @Override
        public void run() {
            try (ServerSocket ss = new ServerSocket(PORTA_RICEZIONE_TCP)) {
                while (true) {
                    Socket s = ss.accept();
                    new ThreadStato(s).start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private class ThreadStato extends Thread {
            private Socket s;

            ThreadStato(Socket s) {
                this.s = s;
            }

            @Override
            public void run() {
                try (PrintWriter out = new PrintWriter(s.getOutputStream(), true);
                ObjectInputStream in = new ObjectInputStream(s.getInputStream())) {
                    if (!orarioBuono()) {
                        out.println("Siamo chiusi");
                        return;
                    }

                    StatoSensore rec = (StatoSensore) in.readObject();
                    boolean buono = verifica(rec);

                    if (buono) {
                        synchronized (Server.this) {
                            if (!archivio.containsKey(rec.getId())) {
                                archivio.put(rec.getId(), new LinkedList<>());
                            }

                            rec.setProg(prog++);
                            archivio.get(rec.getId()).add(rec);
                        }

                        out.println(rec.getProg());
                        inviaNotificaTranne(s.getInetAddress(), rec);
                    } else {
                        out.println("Misura rifiutata");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            private boolean orarioBuono() {
                Calendar now = Calendar.getInstance();
                int hour = now.get(Calendar.HOUR_OF_DAY);

                return hour >= ORA_INIZIO && hour <= ORA_FINE;
            }

            private boolean verifica(StatoSensore rec) {
                double temp = rec.getTemperatura();
                double umidita = rec.getUmidita();

                List<StatoSensore> archivioSensore = archivio.get(rec.getId());

                double mediaUmS = 0.0;
                double mediaTempTutti = 0.0;

                double sommaUmS = 0.0;
                int count = 0;
                for (StatoSensore stato : archivioSensore) {
                    sommaUmS += stato.getUmidita();
                    count++;
                }
                mediaUmS = count == 0 ? 0 : sommaUmS/count;

                double sommaTempTutti = 0.0;
                count = 0;
                for (List<StatoSensore> listaStati : archivio.values()) {
                    for (StatoSensore stato : listaStati) {
                        sommaTempTutti += stato.getTemperatura();
                        count++;
                    }
                }
                mediaTempTutti = count == 0 ? 0 : sommaTempTutti/count;

                double lowUmidita = mediaUmS * EPS_N;
                double uppUmidita = mediaUmS * EPS_P;
                double lowTemp = mediaTempTutti * EPS_N;
                double uppTemp = mediaTempTutti * EPS_P;

                boolean umiditaOk = umidita <= lowUmidita || umidita >= uppUmidita;
                boolean tempOk = temp <= lowTemp || temp >= uppTemp;

                return umiditaOk && tempOk;
            }

            private void inviaNotificaTranne(InetAddress escluso, StatoSensore rec) {
                try (DatagramSocket ds = new DatagramSocket()) {
                    String mess = rec.getId() + "#" + rec.getProg() + "#" + "Temperatura:" + rec.getTemperatura() + "Umidità:" + rec.getUmidita();
                    byte[] buf = mess.getBytes();

                    for (InetAddress addr : indirizziNotifica) {
                        if (!addr.equals(escluso)) {
                            DatagramPacket packet = new DatagramPacket(buf, buf.length, addr, PORTA_RICEZIONE_NOTIFICA_UDP);
                            ds.send(packet);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class ThreadRichiestaNotifiche extends Thread {
        @Override
        public void run() {
            try (ServerSocket ss = new ServerSocket(PORTA_RICHIESTA_NOTIFICA_TCP)) {
                while (true) {
                    Socket s = ss.accept();
                    new ThreadRichiestaNotifica(s).start();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private class ThreadRichiestaNotifica extends Thread {
            private Socket s;

            ThreadRichiestaNotifica(Socket s) {
                this.s = s;
            }

            @Override
            public void run() {
                synchronized (Server.this) {
                    indirizziNotifica.add(s.getInetAddress());
                }
            }
        }
    }

    public static void main(String[] args) {
        new Server().start();
    }
}
