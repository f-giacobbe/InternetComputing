package it.ingsw.rest.odtPag12_;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AssicurazioneService {
    private List<Compagnia> compagnie;

    public Compagnia compagniaMinima(String codicePolizza) {
        Compagnia compMin = null;
        Polizza polMin = null;

        for (Compagnia c : compagnie) {
            for (Polizza p : c.getPolizze()) {
                if (p.getCodice().equals(codicePolizza) && (compMin == null ||
                        p.getPremio() < polMin.getPremio() ||
                        p.getPremio() == polMin.getPremio() && p.getCopertura() > polMin.getCopertura() ||
                        p.getPremio() == polMin.getPremio() && p.getCopertura() == polMin.getCopertura() && p.getCodice().compareTo(polMin.getCodice()) < 0)) {
                    compMin = c;
                    polMin = p;
                }
            }
        }

        return compMin;
    }

    public Map<String, Double> totaleCompagnia(String nome, String sede) {
        Compagnia comp = null;

        for (Compagnia c : compagnie) {
            if (c.getNome().equals(nome) && c.getSede().equals(sede)) {
                comp = c;
                break;
            }
        }

        if (comp == null) {
            return null;
        }

        double tot = 0.0;

        for (Polizza p : comp.getPolizze()) {
            tot += p.getPremio();
        }

        return Map.of("valoreTotale", tot);
    }
}
