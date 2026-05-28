package it.unical.dimes.reti.soluzioni_reti_tutte_java._20210318;
public class MainNegozio {
    public static void main(String[] args) throws Exception {
        Negozio negozio = new Negozio("Negozio1");
        negozio.aggiungiProdotto("Smartphone XYZ", 60.0);
        negozio.aggiungiProdotto("Tablet ABC", 120.0);
        negozio.avvia();
    }
}
