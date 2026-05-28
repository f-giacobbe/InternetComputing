package it.unical.dimes.reti.soluzioni_reti_tutte_java._20240317;
import java.net.Socket;

public class ThreadStatoSensore extends Thread {
    private Socket socket;
    private Server server;

    public ThreadStatoSensore(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        server.gestisciStato(socket);
    }
}
