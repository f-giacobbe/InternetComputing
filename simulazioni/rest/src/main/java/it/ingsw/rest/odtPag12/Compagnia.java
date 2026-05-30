package it.ingsw.rest.odtPag12;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;

import java.io.Serializable;
import java.util.List;

@Data
public class Compagnia {
    private int id;

    private String nome;

    private String sede;

    private List<Polizza> polizze;
}
