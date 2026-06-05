package it.unical.dimes.reti.simulazioni._20230208.es1;

import java.util.Calendar;
import java.util.HashMap;

public class LibreriaService {
    private HashMap<String, Libreria> librerie;     // chiave: partita iva
    private HashMap<String, Libro> libri;           // chiave: ISBN
    private HashMap<Libreria, HashMap<Calendar, HashMap<Libro, Integer>>> venditeLibrerie;     // Libreria, Data, Libro, Vendite

    public int venditeISBN(String pIva, String ISBN) {
        Calendar start = Calendar.getInstance();
        start.add(Calendar.DAY_OF_MONTH, -30);  // 30 giorni fa

        Libreria libreria = librerie.get(pIva);
        Libro libro = libri.get(ISBN);

        int vendite = 0;

        for (Calendar c : venditeLibrerie.get(libreria).keySet()) {
            if (!c.before(start) && venditeLibrerie.get(libreria).get(c).containsKey(libro)) {
                vendite += venditeLibrerie.get(libreria).get(c).get(libro);
            }
        }

        return vendite;
    }

    public Libreria venditeCategoria(String cat) {
        Libreria libMax = null;
        int venditeMax = 0;

        for (Libreria lib : venditeLibrerie.keySet()) {
            int venditeCat = 0;

            for (HashMap<Libro, Integer> venditeLibro : venditeLibrerie.get(lib).values()) {
                for (Libro l : venditeLibro.keySet()) {
                    if (l.getCategoria().equals(cat)) {
                        venditeCat += venditeLibro.get(l);
                    }
                }
            }

            if (venditeCat > venditeMax) {
                venditeMax = venditeCat;
                libMax = lib;
            }
        }

        return libMax;
    }
}
