package it.unical.dimes.reti.soluzioni_reti_tutte_java._20230111;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

public class Server {
    public static final int PORTA_TCP_CLIENT = 3000;
    public static final int PORTA_UDP_SENSORI = 4000;
    public static final int PORTA_MULTICAST = 5000;
    public static final String GRUPPO_MULTICAST = "230.0.0.1";
    public static final long DIECI_MINUTI = 10 * 60 * 1000;

    private ArchivioMisure archivio = new ArchivioMisure();

    public void avvia() throws IOException {
        new RicevitoreMisureUDP(archivio).start();
        new ThreadControlloSensori(archivio).start();

        ServerSocket serverSocket = new ServerSocket(PORTA_TCP_CLIENT);
        System.out.println("Server avviato sulla porta TCP " + PORTA_TCP_CLIENT);

        while (true) {
            Socket socketClient = serverSocket.accept();
            new GestoreClientTCP(socketClient, archivio).start();
        }
    }

    public static void main(String[] args) throws IOException {
        new Server().avvia();
    }
}
