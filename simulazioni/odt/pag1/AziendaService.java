package it.unical.dimes.reti.simulazioni.odt.pag1;

public interface AziendaService {
    void vendita(int idAzienda, String tipoMarmellata, double importo);
    IncassoProdotto maggioreIncasso(int idAzienda);
}
