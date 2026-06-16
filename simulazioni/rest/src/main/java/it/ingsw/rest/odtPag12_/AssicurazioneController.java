package it.ingsw.rest.odtPag12_;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AssicurazioneController {
    @Autowired
    private AssicurazioneService service;

    @GetMapping("/polizze/{codicePolizza}/compagnia-minima")
    public Compagnia compagniaMinima(@PathVariable String codicePolizza) {
        return service.compagniaMinima(codicePolizza);
    }

    @GetMapping("/compagnie/valore")
    public Map<String, Double> totaleCompagnia(@RequestParam String nome, @RequestParam String sede) {
        return service.totaleCompagnia(nome, sede);
    }
}
