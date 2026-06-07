package it.unical.dimes.reti.simulazioni._20230711.es1;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class OrtaggiService {
    private HashMap<Grossista, HashMap<String, PrezzoOrtaggio>> prezziGrossisti;   // grossista, ortaggio, prezzo
    private HashMap<String, Grossista> grossistiPerPIva;   // partita iva, grossista
    private HashMap<String, Set<Grossista>> grossistiPerProvincia;   //provincia, grossisti

    public void updatePrezzo(PrezzoProdotto prezzoProdotto) {
        String partitaIva = prezzoProdotto.getPartitaIva();
        String ortaggio = prezzoProdotto.getNomeOrtaggio();
        double prezzo = prezzoProdotto.getPrezzo();

        Grossista grossista = grossistiPerPIva.get(partitaIva);

        HashMap<String, PrezzoOrtaggio> prezziGrossista = prezziGrossisti.get(grossista);
        prezziGrossista.put(ortaggio, new PrezzoOrtaggio(prezzo, Calendar.getInstance().get(Calendar.YEAR)));
    }

    public String minPrezzoMedio(String ortaggio) {
        String provinciaMin = "";
        double prezzoMedioMin = Double.MAX_VALUE;

        for (String provincia : grossistiPerProvincia.keySet()) {
            double somma = 0;
            int count = 0;

            for (Grossista grossista : grossistiPerProvincia.get(provincia)) {
                HashMap<String, PrezzoOrtaggio> prezziGrossista = prezziGrossisti.get(grossista);

                for (String o : prezziGrossista.keySet()) {
                    if (o.equals(ortaggio) && prezziGrossista.get(o).getAnno() == Calendar.getInstance().get(Calendar.YEAR)) {
                        somma += prezziGrossista.get(o).getPrezzo();
                        count++;
                    }
                }
            }

            double media = Integer.MAX_VALUE;

            if (count != 0) {
                media = somma/count;
            } else

            if (media < prezzoMedioMin) {
                prezzoMedioMin = media;
                provinciaMin = provincia;
            }
        }

        return provinciaMin;
    }
}
