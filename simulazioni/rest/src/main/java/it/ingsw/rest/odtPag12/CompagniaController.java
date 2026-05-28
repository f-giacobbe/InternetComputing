package it.ingsw.rest.odtPag12;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.Serializable;

@RestController
public class CompagniaController implements Serializable {
    @Autowired
    private CompagniaService cs;

    // Endpoint 1
    @GetMapping("/polizze/{codicePolizza}")
    public Compagnia compagniaPrezzoMin(@PathVariable String codicePolizza) {
        return cs.compagniaPrezzoMin(codicePolizza);
    }

    // Endpoint 2
    @GetMapping("/compagnie/valore")
    public ValoreTotaleResponse totalePolizze(@RequestParam String nome, @RequestParam String sede) {
        // Serve il DTO perché vuole scritto "valoreTotale" nel Json (leggi bene traccia)
        return cs.totalePolizze(nome, sede);
    }
}
