package it.unical.dimes.reti.soluzioni_reti_tutte_java._20230111;
public class MainClient {
    public static void main(String[] args) {
        Client client = new Client("localhost");
        Misura misura = client.richiediMisura("S1");
        System.out.println("Risposta del server: " + misura);
    }
}
