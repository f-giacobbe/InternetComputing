package it.unical.dimes.reti.ese8WSDL;

import java.io.Serializable;

public class Prodotto implements Serializable {
    private String codice;
    private String nome;
    private String produttore;
    private double prezzo;

    public Prodotto(String codice, String nome, String produttore, double prezzo) {
        this.codice = codice;
        this.nome = nome;
        this.produttore = produttore;
        this.prezzo = prezzo;
    }

    public String getCodice() {
        return codice;
    }

    public void setCodice(String codice) {
        this.codice = codice;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProduttore() {
        return produttore;
    }

    public void setProduttore(String produttore) {
        this.produttore = produttore;
    }

    public double getPrezzo() {
        return prezzo;
    }

    public void setPrezzo(double prezzo) {
        this.prezzo = prezzo;
    }

    @Override
    public String toString() {
        return "Prodotto{" +
                "codice='" + codice + '\'' +
                ", nome='" + nome + '\'' +
                ", produttore='" + produttore + '\'' +
                ", prezzo=" + prezzo +
                '}';
    }
}
