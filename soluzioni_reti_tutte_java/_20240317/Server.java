package it.unical.dimes.reti.soluzioni_reti_tutte_java._20240317;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.LinkedList;

public class Server {
    public static final String HOSTNAME = "agricoltura.dimes.unical.it";
    public static final int PORTA_STATO = 3000;
    public static final int PORTA_REGISTRAZIONE = 4000;
    public static final int PORTA_UDP_CLIENT = 4000;

    private ArchivioStati archivio = new ArchivioStati();

    public void avvia() throws IOException {
        new ThreadRegistrazioni(this).start();

        ServerSocket serverStato = new ServerSocket(PORTA_STATO);
        System.out.println("Server agricolo avviato sulla porta " + PORTA_STATO);

        while (true) {
            Socket socket = serverStato.accept();
            new ThreadStatoSensore(socket, this).start();
        }
    }

    public void gestisciRegistrazione(Socket socket) {
        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            String idSensore = (String) in.readObject();
            archivio.registraSensore(idSensore, socket.getInetAddress());
            out.writeObject("Registrazione completata per " + idSensore);
            out.flush();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void gestisciStato(Socket socket) {
        try {
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());

            StatoSensore stato = (StatoSensore) in.readObject();

            if (!orarioConsentito()) {
                out.writeObject(new RispostaServer(false, "Rifiutato: fuori orario", -1));
                out.flush();
                socket.close();
                return;
            }

            RispostaServer risposta = archivio.verificaESalva(stato);
            out.writeObject(risposta);
            out.flush();
            socket.close();

            if (risposta.isAccettato()) {
                inviaNotifiche(stato);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean orarioConsentito() {
        Calendar cal = Calendar.getInstance();
        int ora = cal.get(Calendar.HOUR_OF_DAY);
        return ora >= 8 && ora <= 13;
    }

    private void inviaNotifiche(StatoSensore stato) {
        LinkedList<Sottoscrizione> destinatari = archivio.getRegistratiDiversiDa(stato.getIdSensore());
        String messaggio = stato.getIdSensore() + "#" + stato.getNumeroProgressivo() +
                "#T=" + stato.getTemperaturaAria() + ",U=" + stato.getUmiditaSuolo();

        try {
            DatagramSocket udp = new DatagramSocket();
            byte[] buffer = messaggio.getBytes();
            for (int i = 0; i < destinatari.size(); i++) {
                Sottoscrizione s = destinatari.get(i);
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length, s.getIndirizzo(), PORTA_UDP_CLIENT);
                udp.send(packet);
            }
            udp.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
