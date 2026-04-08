package it.unical.dimes.reti.ese1.escasa;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;

public class SendObjectAdv {
    // Simulo un database
    HashMap<Integer, Studente> dbStudenti = new HashMap<>();


    public static void main(String[] args) {
        new SendObjectAdv().test();
    }


    public void test() {
        dbStudenti.put(10010, new Studente(10010, "Francesco", "Giacobbe", "ingegneria informatica"));
        dbStudenti.put(20020, new Studente(20020, "Abdul", "Mohammed", "filosofia"));
        dbStudenti.put(30030, new Studente(30030, "Charles", "Leclerc", "ingegneria meccanica"));

        try {
            ServerSocket s = new ServerSocket(8189);

            while (true) {
                System.out.println(s + " waiting for connection.");
                Socket incoming = s.accept();
                new ThreadedHandler(incoming).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



    class ThreadedHandler extends Thread {
        private final Socket incoming;

        ThreadedHandler(Socket incoming) {
            this.incoming = incoming;
        }

        @Override
        public void run() {
            System.out.println(incoming);
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(incoming.getInputStream()));
                ObjectOutputStream out = new ObjectOutputStream(incoming.getOutputStream());

                while (true) {
                    String line = in.readLine();

                    if (line == null || line.isEmpty()) {
                        break;
                    }

                    int matricola = Integer.parseInt(line);
                    Studente studente = dbStudenti.get(matricola);
                    out.writeObject(studente);
                }

                out.close();
                in.close();
                incoming.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}