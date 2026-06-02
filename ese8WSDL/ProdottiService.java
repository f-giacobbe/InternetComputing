package it.unical.dimes.reti.ese8WSDL;

import java.util.HashMap;
import java.util.LinkedList;

interface ProdottiService {
    Prodotto prodottoPiuVenduto(String produttore);
    ListaProdotti prodottiMaxIncasso(String magazzino);
}


class ProdottiServiceImpl implements ProdottiService {
    private HashMap<String, HashMap<Prodotto, Integer>> venditePerMagazzino;
                //  magazzino,    prodotto,   vendite di prodotto in magazzino


    @Override
    public Prodotto prodottoPiuVenduto(String produttore) {
        Prodotto piuVenduto = null;
        int venditeMax = 0;

        for (String magazzino : venditePerMagazzino.keySet()) {
            for (Prodotto prodotto : venditePerMagazzino.get(magazzino).keySet()) {
                if (!prodotto.getProduttore().equals(produttore)) {
                    continue;
                }

                int vendite = venditePerMagazzino.get(magazzino).get(prodotto);

                if (vendite > venditeMax) {
                    venditeMax = vendite;
                    piuVenduto = prodotto;
                }
            }
        }

        return piuVenduto;
    }

    @Override
    public ListaProdotti prodottiMaxIncasso(String magazzino) {
        int n = 3;
        ListaProdotti res = new ListaProdotti();
        LinkedList<Prodotto> lista = res.getLista();

        HashMap<Prodotto, Integer> prodottiMagazzino = new HashMap<>(venditePerMagazzino.get(magazzino));   // copia

        for (int i = 0; i < n; i++) {
            // Se il magazzino ha meno di 3 prodotti
            if (prodottiMagazzino.isEmpty()) {
                break;
            }

            Prodotto prodottoIncassoMax = null;
            double incassoMax = 0;

            for (Prodotto prodotto : prodottiMagazzino.keySet()) {
                int vendite = prodottiMagazzino.get(prodotto);
                double prezzo = prodotto.getPrezzo();
                double incasso = vendite * prezzo;

                if (incasso > incassoMax) {
                    incassoMax = incasso;
                    prodottoIncassoMax = prodotto;
                }
            }

            lista.add(prodottoIncassoMax);
            prodottiMagazzino.remove(prodottoIncassoMax);
        }

        return res;
    }
}
