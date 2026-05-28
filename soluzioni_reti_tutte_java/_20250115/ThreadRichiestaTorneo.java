package it.unical.dimes.reti.soluzioni_reti_tutte_java._20250115;
import java.net.Socket;

public class ThreadRichiestaTorneo extends Thread {
    private Socket socket;
    private ServerTorneo server;

    public ThreadRichiestaTorneo(Socket socket, ServerTorneo server) {
        this.socket = socket;
        this.server = server;
    }

    public void run() {
        server.gestisciRichiesta(socket);
    }
}
