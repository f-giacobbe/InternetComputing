package it.unical.dimes.reti.soluzioni_reti_tutte_java._20210318;

import java.util.HashMap;

public class ArchivioVendite {
    private HashMap<String, Double> vendite = new HashMap<String, Double>();

    public synchronized void aggiungiVendita(String idNegozio, double importo) {
        double totale = 0;
        if (vendite.containsKey(idNegozio)) {
            totale = vendite.get(idNegozio);
        }
        vendite.put(idNegozio, totale + importo);
    }

    public synchronized double getTotale(String idNegozio) {
        if (!vendite.containsKey(idNegozio)) {
            return 0;
        }
        return vendite.get(idNegozio);
    }
}
