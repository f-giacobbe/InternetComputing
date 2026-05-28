package it.unical.dimes.reti.soluzioni_reti_tutte_java._20210318;
import java.net.Socket;

public class ThreadRichiestaNegozio extends Thread {
    private Socket socket;
    private Negozio negozio;

    public ThreadRichiestaNegozio(Socket socket, Negozio negozio) {
        this.socket = socket;
        this.negozio = negozio;
    }

    public void run() {
        negozio.gestisciRichiesta(socket);
    }
}
