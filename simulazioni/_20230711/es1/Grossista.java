package it.unical.dimes.reti.simulazioni._20230711.es1;

public class Grossista {
    private String partitaIva;
    private String provincia;

    public Grossista(String pIva, String provincia) {
        this.partitaIva = pIva;
        this.provincia = provincia;
    }

    public String getPartitaIva() {
        return partitaIva;
    }

    public String getProvincia() {
        return provincia;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Grossista g) {
            return partitaIva.equals(g.getPartitaIva()) && provincia.equals(g.getProvincia());
        }

        return false;
    }
}
