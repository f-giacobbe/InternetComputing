package it.ingsw.rest.odtPag12;

import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

@Service
public class CompagniaService {
    private List<Compagnia> compagnie;

    Compagnia compagniaPrezzoMin(String pId) {
        Compagnia min = null;
        double premioMin = 0;
        double coperturaMax = 0;

        for (Compagnia c : compagnie) {
            for (Polizza p : c.getPolizze()) {
                if (p.getId().equals(pId)) {
                    double premio = p.getPremioAnnuo();
                    double copertura = p.getCoperturaMax();

                    if (min == null || premio < premioMin || premio == premioMin && copertura > coperturaMax || premio == premioMin && copertura == coperturaMax && c.getId() < min.getId()) {
                        min = c;
                        premioMin = premio;
                        coperturaMax = copertura;
                    }
                }
            }
        }

        return min;
    }

    ValoreTotaleResponse totalePolizze(String nome, String sede) {
        ValoreTotaleResponse res = new ValoreTotaleResponse();
        double val = 0;

        Compagnia compagnia = null;
        for (Compagnia c : compagnie) {
            if (c.getNome().equals(nome) && c.getSede().equals(sede)) {
                compagnia = c;
                break;
            }
        }

        if (compagnia == null) {
            return null;
        }

        for (Polizza p : compagnia.getPolizze()) {
            val += p.getPremioAnnuo();
        }

        res.setValoreTotale(val);
        return res;
    }
}
