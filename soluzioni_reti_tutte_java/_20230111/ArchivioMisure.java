package it.unical.dimes.reti.soluzioni_reti_tutte_java._20230111;
import java.util.HashMap;
import java.util.LinkedList;

public class ArchivioMisure {
    private HashMap<String, Misura> ultimeMisure = new HashMap<String, Misura>();
    private LinkedList<String> sensoriConosciuti = new LinkedList<String>();

    public synchronized void salvaMisura(Misura misura) {
        if (!ultimeMisure.containsKey(misura.getIdSensore())) {
            sensoriConosciuti.add(misura.getIdSensore());
        }
        ultimeMisure.put(misura.getIdSensore(), misura);
    }

    public synchronized Misura getUltimaMisura(String idSensore) {
        return ultimeMisure.get(idSensore);
    }

    public synchronized LinkedList<String> getSensoriNonFunzionanti(long tempoMassimoSenzaMisure) {
        LinkedList<String> risultato = new LinkedList<String>();
        long now = System.currentTimeMillis();

        for (int i = 0; i < sensoriConosciuti.size(); i++) {
            String id = sensoriConosciuti.get(i);
            Misura m = ultimeMisure.get(id);
            if (m != null && now - m.getTimestamp() > tempoMassimoSenzaMisure) {
                risultato.add(id);
            }
        }
        return risultato;
    }
}
