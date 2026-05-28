package it.unical.dimes.reti.soluzioni_reti_tutte_java._20250115;
public class MainClient {
    public static void main(String[] args) {
        ClientTorneo client = new ClientTorneo("Client01", "localhost");
        client.avviaRicezioneMulticast();

        RispostaTorneo r1 = client.inserisciRisultato("Match001", "FC-Milano", "Torino-United", 3, 1, "2025-01-15 20:45");
        System.out.println(r1);

        RispostaTorneo r2 = client.richiediClassifica();
        System.out.println(r2);
    }
}
