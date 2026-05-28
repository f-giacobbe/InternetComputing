package it.unical.dimes.reti.soluzioni_reti_tutte_java._20240317;
import java.net.ServerSocket;
import java.net.Socket;

public class ThreadRegistrazioni extends Thread {
    private Server server;

    public ThreadRegistrazioni(Server server) {
        this.server = server;
    }

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(Server.PORTA_REGISTRAZIONE);
            System.out.println("Servizio registrazioni avviato sulla porta " + Server.PORTA_REGISTRAZIONE);
            while (true) {
                Socket socket = serverSocket.accept();
                new ThreadRegistrazioneSingola(socket, server).start();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
