package it.unical.dimes.reti.ese8WSDL;

import java.io.Serializable;
import java.util.LinkedList;

public class ListaProdotti implements Serializable {
    private LinkedList<Prodotto> lista = new LinkedList<>();

    public LinkedList<Prodotto> getLista() {
        return lista;
    }
}
