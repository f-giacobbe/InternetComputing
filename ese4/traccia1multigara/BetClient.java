package it.unical.dimes.reti.ese4.traccia1multigara;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.Socket;

public class BetClient {
    private InetAddress server;
    private InetAddress multicast;
    private int serverPort;
    private int multicastPort;

    public BetClient(InetAddress server, InetAddress multicast, int serverPort, int multicastPort) {
        this.server = server;
        this.multicast = multicast;
        this.serverPort = serverPort;
        this.multicastPort = multicastPort;
    }

    public boolean inviaScommessa(int gara, int cavallo, int importo) {
        try {
            Socket s = new Socket(server, serverPort);
            BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
            PrintWriter out = new PrintWriter(s.getOutputStream(), true);

            out.println(gara + " " + cavallo + " " + importo);

            String risposta = in.readLine();

            return risposta.equals("true");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String riceviVincitori() {
        try {
            MulticastSocket s = new MulticastSocket(multicastPort);
            s.joinGroup(multicast);

            byte[] buf = new byte[256];
            DatagramPacket packet = new DatagramPacket(buf, buf.length);
            s.receive(packet);

            return new String(packet.getData(), 0, packet.getLength());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        try {
            InetAddress server = InetAddress.getByName("localhost");
            InetAddress multicast = InetAddress.getByName("230.0.0.1");
            int serverPort = 8001;
            int multicastPort = 8002;

            BetClient client = new BetClient(server, multicast, serverPort, multicastPort);

            int gara = 1;
            int cavallo = 7;
            int importo = 100;

            if (client.inviaScommessa(gara, cavallo, importo)) {
                client.riceviVincitori();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
