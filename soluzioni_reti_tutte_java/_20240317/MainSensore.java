package it.unical.dimes.reti.soluzioni_reti_tutte_java._20240317;
public class MainSensore {
    public static void main(String[] args) {
        Sensore sensore = new Sensore("S1", "localhost");
        sensore.registraNotifiche();
        sensore.avviaRicezioneNotifiche();
        sensore.inviaStato(25.0, 40.0);
    }
}
