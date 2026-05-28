package it.unical.dimes.reti.soluzioni_reti_tutte_java._20241108;
import java.util.LinkedList;

public class ArchivioConversioni {
    private LinkedList<Conversione> conversioni = new LinkedList<Conversione>();

    public synchronized void aggiungi(Conversione conversione) {
        conversioni.add(conversione);
    }

    public synchronized LinkedList<Conversione> getConversioniDopo(long timestampMinimo) {
        LinkedList<Conversione> risultato = new LinkedList<Conversione>();
        for (int i = 0; i < conversioni.size(); i++) {
            Conversione c = conversioni.get(i);
            if (c.getTimestamp() >= timestampMinimo) {
                risultato.add(c);
            }
        }
        return risultato;
    }
}
