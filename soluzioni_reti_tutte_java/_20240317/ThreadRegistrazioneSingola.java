package it.unical.dimes.reti.soluzioni_reti_tutte_java._20240317;
import java.net.Socket;

public class ThreadRegistrazioneSingola extends Thread {
    private Socket socket;
    private Server server;

    public ThreadRegistrazioneSingola(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        server.gestisciRegistrazione(socket);
    }
}
