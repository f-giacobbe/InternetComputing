package it.unical.dimes.reti.simulazioni._20230711.es2;

public class ProdottoNazione {
    private String prodotto;
    private String nazione;

    public ProdottoNazione(String prodotto, String nazione) {
        this.prodotto = prodotto;
        this.nazione = nazione;
    }

    public String getProdotto() {
        return prodotto;
    }

    public String getNazione() {
        return nazione;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof ProdottoNazione pn) {
            return prodotto.equals(pn.getProdotto()) && nazione.equals(pn.getNazione());
        }

        return false;
    }
}
