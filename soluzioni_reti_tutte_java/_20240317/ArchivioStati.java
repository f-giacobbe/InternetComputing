package it.unical.dimes.reti.soluzioni_reti_tutte_java._20240317;
import java.net.InetAddress;
import java.util.HashMap;
import java.util.LinkedList;

public class ArchivioStati {
    private HashMap<String, LinkedList<StatoSensore>> statiPerSensore = new HashMap<String, LinkedList<StatoSensore>>();
    private HashMap<String, Integer> progressivi = new HashMap<String, Integer>();
    private HashMap<String, InetAddress> registrati = new HashMap<String, InetAddress>();

    public synchronized void registraSensore(String idSensore, InetAddress indirizzo) {
        registrati.put(idSensore, indirizzo);
    }

    public synchronized RispostaServer verificaESalva(StatoSensore stato) {
        double mediaUmiditaSensore = calcolaMediaUmidita(stato.getIdSensore());
        double mediaTemperaturaGlobale = calcolaMediaTemperaturaGlobale();

        boolean esistonoUmidita = contieneStatiDelSensore(stato.getIdSensore());
        boolean esistonoTemperature = contaStatiTotali() > 0;

        boolean umiditaSimile = esistonoUmidita && vicino(stato.getUmiditaSuolo(), mediaUmiditaSensore);
        boolean temperaturaSimile = esistonoTemperature && vicino(stato.getTemperaturaAria(), mediaTemperaturaGlobale);

        if (umiditaSimile && temperaturaSimile) {
            return new RispostaServer(false, "Rifiutato: valori troppo simili alle medie", -1);
        }

        int progressivo = 1;
        if (progressivi.containsKey(stato.getIdSensore())) {
            progressivo = progressivi.get(stato.getIdSensore()) + 1;
        }
        progressivi.put(stato.getIdSensore(), progressivo);
        stato.setNumeroProgressivo(progressivo);

        LinkedList<StatoSensore> lista = statiPerSensore.get(stato.getIdSensore());
        if (lista == null) {
            lista = new LinkedList<StatoSensore>();
            statiPerSensore.put(stato.getIdSensore(), lista);
        }
        lista.add(stato);

        return new RispostaServer(true, "Accettato", progressivo);
    }

    public synchronized LinkedList<Sottoscrizione> getRegistratiDiversiDa(String idSensore) {
        LinkedList<Sottoscrizione> risultato = new LinkedList<Sottoscrizione>();
        for (String id : registrati.keySet()) {
            if (!id.equals(idSensore)) {
                risultato.add(new Sottoscrizione(id, registrati.get(id)));
            }
        }
        return risultato;
    }

    private boolean contieneStatiDelSensore(String idSensore) {
        LinkedList<StatoSensore> lista = statiPerSensore.get(idSensore);
        return lista != null && lista.size() > 0;
    }

    private int contaStatiTotali() {
        int totale = 0;
        for (String id : statiPerSensore.keySet()) {
            totale += statiPerSensore.get(id).size();
        }
        return totale;
    }

    private double calcolaMediaUmidita(String idSensore) {
        LinkedList<StatoSensore> lista = statiPerSensore.get(idSensore);
        if (lista == null || lista.size() == 0) return 0;

        double somma = 0;
        for (int i = 0; i < lista.size(); i++) {
            somma += lista.get(i).getUmiditaSuolo();
        }
        return somma / lista.size();
    }

    private double calcolaMediaTemperaturaGlobale() {
        double somma = 0;
        int conta = 0;
        for (String id : statiPerSensore.keySet()) {
            LinkedList<StatoSensore> lista = statiPerSensore.get(id);
            for (int i = 0; i < lista.size(); i++) {
                somma += lista.get(i).getTemperaturaAria();
                conta++;
            }
        }
        if (conta == 0) return 0;
        return somma / conta;
    }

    private boolean vicino(double valore, double media) {
        if (media == 0) return valore == 0;
        return Math.abs(valore - media) <= Math.abs(media) * 0.05;
    }
}
