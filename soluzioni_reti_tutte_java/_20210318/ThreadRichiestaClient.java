package it.unical.dimes.reti.soluzioni_reti_tutte_java._20210318;
import java.net.Socket;

public class ThreadRichiestaClient extends Thread {
    private Socket socket;
    private Server server;

    public ThreadRichiestaClient(Socket socket, Server server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        server.gestisciRichiestaClient(socket);
    }
}
