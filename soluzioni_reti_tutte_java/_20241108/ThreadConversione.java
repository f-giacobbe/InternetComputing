package it.unical.dimes.reti.soluzioni_reti_tutte_java._20241108;
import java.net.Socket;

public class ThreadConversione extends Thread {
    private Socket socket;
    private Server server;

    public ThreadConversione(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        server.gestisciRichiesta(socket);
    }
}
