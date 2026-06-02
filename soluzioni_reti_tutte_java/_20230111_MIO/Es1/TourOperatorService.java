package it.unical.dimes.reti.soluzioni_reti_tutte_java._20230111_MIO.Es1;

import java.util.Calendar;
import java.util.HashMap;

public class TourOperatorService {
    private HashMap<Struttura, HashMap<Integer, Integer>> prenotazioniAnnueStruttura;   // Struttura, Anno, Prenotazioni
    private HashMap<Struttura, Integer> postiLiberiStruttura;   // Struttura, Posti attualmente liberi

    public int numeroPersone(String nomeStruttura) {
        Struttura struttura = null;
        for (Struttura s : prenotazioniAnnueStruttura.keySet()) {
            if (s.getNome().equals(nomeStruttura)) {
                struttura = s;
                break;
            }
        }

        if (struttura == null) {
            return 0;
        }

        int annoCorrente = Calendar.getInstance().get(Calendar.YEAR);
        return prenotazioniAnnueStruttura.get(struttura).get(annoCorrente);
    }

    public Struttura miglioreStruttura(String citta, int stelle) {
        Struttura struttura = null;
        int postiMin = Integer.MAX_VALUE;

        for (Struttura s : postiLiberiStruttura.keySet()) {
            if (s.getCitta().equals(citta) && s.getStelle() == stelle && postiLiberiStruttura.get(s) < postiMin) {
                struttura = s;
                postiMin = postiLiberiStruttura.get(s);
            }
        }

        return struttura;
    }
}
