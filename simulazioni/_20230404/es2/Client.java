package it.unical.dimes.reti.simulazioni._20230404.es2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.net.*;

public class Client {
    private int P;
    private String cf = "aaabbb";

    private Asta attendiAnnuncioAsta() {
        try (MulticastSocket ms = new MulticastSocket(Server.PORTA_MULTICAST)) {
            ms.joinGroup(InetAddress.getByName(Server.IP_MULTICAST));

            byte[] buf = new byte[256];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            ms.receive(packet);

            String[] campi = new String(buf, 0, packet.getLength()).split(" ");
            int idAsta = Integer.parseInt(campi[0]);
            P = Integer.parseInt(campi[1]);
            String prodotto = campi[2];

            return new Asta(idAsta, prodotto);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean partecipa(int P, Offerta offerta) {
        try (Socket s = new Socket(Server.IP, P);
        ObjectOutputStream out = new ObjectOutputStream(s.getOutputStream());
        BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()))) {
            out.writeObject(offerta);
            out.flush();
            return Boolean.parseBoolean(in.readLine());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private boolean attendiEsito() {
        try (DatagramSocket ds = new DatagramSocket(Server.PORTA_UDP)) {
            ds.setSoTimeout(Server.UN_ORA + 100000);    // Se a questo punto non è arrivato nulla presumo di aver perso l'asta

            byte[] buf = new byte[256];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            ds.receive(packet);

            String mess = new String(buf, 0, packet.getLength());
            System.out.println(mess);
            return true;
        } catch (SocketTimeoutException e) {
            return false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        Client client = new Client();
        Asta asta = client.attendiAnnuncioAsta();
        Offerta offerta = new Offerta(client.cf, asta.getId(), 500);
        System.out.println(client.partecipa(client.P, offerta));
        System.out.println(client.attendiEsito());
    }
}
