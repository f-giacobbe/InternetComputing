package it.unical.dimes.reti.simulazioni.odt.pag1;

import java.util.HashMap;

public class AziendaServiceImpl implements AziendaService {
    private HashMap<Integer, HashMap<String, Double>> venditeAziende;   // idAzienda, tipoMarmellata, importoComplessivo

    @Override
    public void vendita(int idAzienda, String tipoMarmellata, double importo) {
        if (!venditeAziende.containsKey(idAzienda)) {
            venditeAziende.put(idAzienda, new HashMap<>());
        }

        if (!venditeAziende.get(idAzienda).containsKey(tipoMarmellata)) {
            venditeAziende.get(idAzienda).put(tipoMarmellata, 0.0);
        }

        double newImporto = venditeAziende.get(idAzienda).get(tipoMarmellata) + importo;
        venditeAziende.get(idAzienda).put(tipoMarmellata, newImporto);
    }

    @Override
    public IncassoProdotto maggioreIncasso(int idAzienda) {
        String tipoMax = null;
        double importoMax = 0.0;

        HashMap<String, Double> venditeAzienda = venditeAziende.get(idAzienda);
        for (String tipo : venditeAzienda.keySet()) {
            double importo = venditeAzienda.get(tipo);

            if (importo > importoMax) {
                tipoMax = tipo;
                importoMax = importo;
            }
        }

        return new IncassoProdotto(tipoMax, importoMax);
    }
}
