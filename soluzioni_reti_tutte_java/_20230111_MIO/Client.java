package it.unical.dimes.reti.soluzioni_reti_tutte_java._20230111_MIO;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    public static Misura richiediMisura(String idSensore) {
        try (Socket s = new Socket(InetAddress.getByName(Server.IP_SERVER), Server.PORTA_CLIENT)) {
            ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(s.getInputStream());

            out.writeObject(idSensore);
            out.flush();

            return (Misura) in.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args) {
        Misura m = richiediMisura("1");
        System.out.println(m);
    }
}
