package it.unical.dimes.reti.soluzioni_reti_tutte_java._20210318;
public class MainClient {
    public static void main(String[] args) {
        Client client = new Client("localhost");
        client.inviaRichiesta("Smartphone XYZ", 2, 140.0);
    }
}
