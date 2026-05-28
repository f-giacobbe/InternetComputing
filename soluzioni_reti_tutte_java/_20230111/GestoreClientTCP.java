package it.unical.dimes.reti.soluzioni_reti_tutte_java._20230111;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class GestoreClientTCP extends Thread {
    private Socket socket;
    private ArchivioMisure archivio;

    public GestoreClientTCP(Socket socket, ArchivioMisure archivio) {
        this.socket = socket;
        this.archivio = archivio;
    }

    public void run() {
        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            String idSensore = (String) in.readObject();
            Misura misura = archivio.getUltimaMisura(idSensore);
            out.writeObject(misura);
            out.flush();

            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
