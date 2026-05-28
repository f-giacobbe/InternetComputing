package it.unical.dimes.reti.soluzioni_reti_tutte_java._20241108;
public class MainClient {
    public static void main(String[] args) {
        Client client = new Client("Client01", "localhost");
        client.avviaRicezioneMulticast();
        client.inviaRichiesta("EUR", "USD", 100.0);
    }
}
