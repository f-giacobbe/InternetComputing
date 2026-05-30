package it.ingsw.rest.odtPag12;

import lombok.Data;

import java.io.Serializable;

@Data
public class Polizza {
    private String id;

    private double premioAnnuo;

    private double coperturaMax;
}
