package it.unical.dimes.reti.simulazioni._20230711.es2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    public static void main(String[] args) {
        String richiesta = "iphone IT false";
        inviaRichiesta(richiesta);
    }

    private static void inviaRichiesta(String richiesta) {
        try (Socket s = new Socket("localhost", Server.TCP_RICHIESTA_CLIENT);
             ObjectInputStream in = new ObjectInputStream(s.getInputStream());
             PrintWriter out = new PrintWriter(s.getOutputStream(), true)) {
            out.println(richiesta);
            OffertaNegozio risposta = (OffertaNegozio) in.readObject();
            System.out.println(risposta);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
