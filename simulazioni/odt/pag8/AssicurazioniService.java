package it.unical.dimes.reti.simulazioni.odt.pag8;

import java.util.List;

public class AssicurazioniService {
    private List<Compagnia> compagnie;

    public Compagnia minPremioPolizza(String codicePolizza) {
        Compagnia compMin = null;
        Polizza polMin = null;

        for (Compagnia c : compagnie) {
            for (Polizza p : c.getListPol()) {
                if (p.getCodice().equals(codicePolizza) && (compMin == null ||
                        p.getPremio() < polMin.getPremio() ||
                        p.getPremio() == polMin.getPremio() && p.getCopertura() > polMin.getCopertura() ||
                        p.getPremio() == polMin.getPremio() && p.getCopertura() == polMin.getCopertura() && c.getCodice() < compMin.getCodice())) {
                    compMin = c;
                    polMin = p;
                }
            }
        }

        return compMin;
    }

    public double valorePolizze(String nome, String sede) {
        Compagnia compagnia = null;

        for (Compagnia c : compagnie) {
            if (c.getNome().equals(nome) && c.getSede().equals(sede)) {
                compagnia = c;
                break;
            }
        }

        if (compagnia == null) {
            return -1.0;
        }

        double tot = 0.0;

        for (Polizza p : compagnia.getListPol()) {
            tot += p.getPremio();
        }

        return tot;
    }
}
