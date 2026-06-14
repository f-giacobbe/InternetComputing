package it.unical.dimes.reti.simulazioni._20230404.es1;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class AziendaService {
    private HashMap<Integer, HashMap<String, List<IncassoProdotto>>> vendite;    // idAzienda, nomeVino, vendite

    public void vendita(int idAzienda, String nomeVino, int quantita, double importo) {
        IncassoProdotto nuovaVendita = new IncassoProdotto(nomeVino, quantita, importo);

        if (!vendite.containsKey(idAzienda)) {
            vendite.put(idAzienda, new HashMap<>());
        }

        HashMap<String, List<IncassoProdotto>> venditeAzienda = vendite.get(idAzienda);

        if (!venditeAzienda.containsKey(nomeVino)) {
            venditeAzienda.put(nomeVino, new LinkedList<>());
        }

        venditeAzienda.get(nomeVino).add(nuovaVendita);
    }

    public IncassoProdotto maggioreIncasso(int idAzienda) {
        IncassoProdotto max = null;

        HashMap<String, List<IncassoProdotto>> venditeAzienda = vendite.get(idAzienda);
        for (String vino : venditeAzienda.keySet()) {
            double incasso = 0;
            int quantita = 0;

            List<IncassoProdotto> venditeVino = venditeAzienda.get(vino);
            for (IncassoProdotto ip : venditeVino) {
                incasso += ip.getImporto();
                quantita += ip.getQuantita();
            }

            if (max == null || max.getImporto() < incasso) {
                max = new IncassoProdotto(vino, quantita, incasso);
            }
        }

        return max;
    }
}
