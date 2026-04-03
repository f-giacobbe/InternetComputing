package it.unical.dimes.reti.ese1.escasa;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

public class SendObjectAdv {
    public static void main(String[] args) {
        // Simulo un database
        HashMap<Integer, Studente> dbStudenti = new HashMap<>();
        dbStudenti.put(10010, new Studente(10010, "Francesco", "Giacobbe", "ingegneria informatica"));
        dbStudenti.put(20020, new Studente(20020, "Abdul", "Mohammed", "filosofia"));
        dbStudenti.put(30030, new Studente(30030, "Charles", "Leclerc", "ingegneria meccanica"));

        try {
            ServerSocket s = new ServerSocket(8189);
            System.out.println(s + " waiting for connection.");
            Socket incoming = s.accept();

            System.out.println(incoming);
            BufferedReader in = new BufferedReader(new InputStreamReader(incoming.getInputStream()));
            ObjectOutputStream objOut = new ObjectOutputStream(incoming.getOutputStream());

            while (true) {
                String line = in.readLine();

                if (line == null) {
                    break;
                }

                int matricola = Integer.parseInt(line);
                Studente studente = dbStudenti.get(matricola);
                objOut.writeObject(studente);
            }
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }
}
