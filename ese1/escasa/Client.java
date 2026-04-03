package it.unical.dimes.reti.ese1.escasa;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) {
        try {
            Socket s = new Socket("localhost", 8189);
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);

            Scanner sc = new Scanner(System.in);

            while (true) {
                String line = sc.nextLine();

                if (line == null || line.equals("BYE")) {
                    break;
                }

                out.println(line);    // Invio matricola
                Studente studente = (Studente) in.readObject();
                System.out.println(studente);
            }

            s.close();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
