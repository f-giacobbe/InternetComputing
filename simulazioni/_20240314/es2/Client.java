package it.unical.dimes.reti.simulazioni._20240314.es2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
    private InetAddress server;
    private int idSensore;

    public Client(InetAddress server, int idSensore) {
        this.server = server;
        this.idSensore = idSensore;
    }

    public String inviaStatoSensore(StatoSensore stato) {
        try (Socket s = new Socket(server, Server.PORTA_RICEZIONE_TCP);
        ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()))) {
            out.writeObject(stato);
            out.flush();
            return in.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void attivaNotifiche() {
        try (Socket s = new Socket(server, Server.PORTA_RICEZIONE_NOTIFICA_UDP);
        PrintWriter out = new PrintWriter(s.getOutputStream(), true)) {
            out.println(idSensore);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void attendiNotifiche() {
        try (DatagramSocket ds = new DatagramSocket(Server.PORTA_RICEZIONE_NOTIFICA_UDP)) {
            byte[] buf = new byte[512];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);

            while (true) {
                ds.receive(packet);
                String mess = new String(buf, 0, packet.getLength());
                System.out.println(mess);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        Client c = new Client(InetAddress.getByName(Server.HOSTNAME), 1);
        StatoSensore stato = new StatoSensore("A1", -1, 25.7, 0.9);
        c.inviaStatoSensore(stato);
        c.attivaNotifiche();
        c.attendiNotifiche();
    }
}
