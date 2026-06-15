package it.ingsw.rest.pag4;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AziendaService {
    private HashMap<Integer, HashMap<String, Double>> venditeAziende;

    public void vendita(int idAzienda, String tipoMarmellata, double importo) {
        if (!venditeAziende.containsKey(idAzienda)) {
            venditeAziende.put(idAzienda, new HashMap<String, Double>());
        }

        HashMap<String, Double> venditeAzienda = venditeAziende.get(idAzienda);

        if (!venditeAzienda.containsKey(tipoMarmellata)) {
            venditeAzienda.put(tipoMarmellata, 0.0);
        }

        double newImporto = venditeAzienda.get(tipoMarmellata) + importo;
        venditeAzienda.put(tipoMarmellata, newImporto);
    }

    public IncassoProdotto migliorIncasso(int idAzienda) {
        if (!venditeAziende.containsKey(idAzienda)) {
            return null;
        }

        String tipoMax = null;
        double importoMax = 0.0;

        var venditeAzienda = venditeAziende.get(idAzienda);
        for (Map.Entry<String, Double> entry : venditeAzienda.entrySet()) {
            String tipo = entry.getKey();
            double importo = entry.getValue();

            if (importo > importoMax) {
                tipoMax = tipo;
                importoMax = importo;
            }
        }

        return new IncassoProdotto(tipoMax, importoMax);
    }
}
