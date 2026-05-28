package it.unical.dimes.reti.soluzioni_reti_tutte_java._20250115;
import java.util.HashMap;
import java.util.LinkedList;

public class ArchivioTorneo {
    private HashMap<String, Boolean> squadreValide = new HashMap<String, Boolean>();
    private HashMap<String, Partita> partite = new HashMap<String, Partita>();
    private HashMap<String, StatisticheSquadra> classifica = new HashMap<String, StatisticheSquadra>();
    private LinkedList<Partita> partiteInserite = new LinkedList<Partita>();

    public ArchivioTorneo() {
        aggiungiSquadra("FC-Milano");
        aggiungiSquadra("Torino-United");
        aggiungiSquadra("Roma");
        aggiungiSquadra("Napoli");
        aggiungiSquadra("Juventus");
        aggiungiSquadra("Inter");
    }

    private void aggiungiSquadra(String nome) {
        squadreValide.put(nome, true);
        classifica.put(nome, new StatisticheSquadra(nome));
    }

    public synchronized RispostaTorneo inserisciPartita(Partita p) {
        if (p == null) {
            return new RispostaTorneo(false, "Errore: partita mancante", null);
        }
        if (partite.containsKey(p.getCodicePartita())) {
            return new RispostaTorneo(false, "Errore: codice partita gia presente", null);
        }
        if (!squadreValide.containsKey(p.getSquadraCasa()) || !squadreValide.containsKey(p.getSquadraOspite())) {
            return new RispostaTorneo(false, "Errore: squadra non esistente", null);
        }
        if (p.getGolCasa() < 0 || p.getGolOspite() < 0 || p.getSquadraCasa().equals(p.getSquadraOspite())) {
            return new RispostaTorneo(false, "Errore: dati partita non validi", null);
        }

        partite.put(p.getCodicePartita(), p);
        partiteInserite.add(p);

        int puntiCasa = 0;
        int puntiOspite = 0;
        if (p.getGolCasa() > p.getGolOspite()) {
            puntiCasa = 3;
        } else if (p.getGolCasa() < p.getGolOspite()) {
            puntiOspite = 3;
        } else {
            puntiCasa = 1;
            puntiOspite = 1;
        }

        classifica.get(p.getSquadraCasa()).aggiorna(p.getGolCasa(), p.getGolOspite(), puntiCasa);
        classifica.get(p.getSquadraOspite()).aggiorna(p.getGolOspite(), p.getGolCasa(), puntiOspite);

        return new RispostaTorneo(true, "Risultato inserito correttamente", copiaClassifica());
    }

    public synchronized HashMap<String, StatisticheSquadra> copiaClassifica() {
        HashMap<String, StatisticheSquadra> copia = new HashMap<String, StatisticheSquadra>();
        for (String squadra : classifica.keySet()) {
            copia.put(squadra, classifica.get(squadra).copia());
        }
        return copia;
    }

    public synchronized LinkedList<Partita> getPartiteDopo(long timestampMinimo) {
        LinkedList<Partita> risultato = new LinkedList<Partita>();
        for (int i = 0; i < partiteInserite.size(); i++) {
            Partita p = partiteInserite.get(i);
            if (p.getTimestampInserimento() >= timestampMinimo) {
                risultato.add(p);
            }
        }
        return risultato;
    }
}
