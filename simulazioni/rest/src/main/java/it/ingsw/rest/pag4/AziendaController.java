package it.ingsw.rest.pag4;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/aziende")
public class AziendaController {
    @Autowired
    private AziendaService service;

    @PostMapping("{idAzienda}/vendite")
    public Map<String, String> vendita(@PathVariable int idAzienda, @RequestBody IncassoProdotto incassoProdotto) {
        service.vendita(idAzienda, incassoProdotto.getTipoMarmellata(), incassoProdotto.getImporto());
        return Map.of("message", "Incasso registrato con successo");
    }

    @GetMapping("{idAzienda}/migliore")
    public IncassoProdotto migliorIncasso(@PathVariable int idAzienda) {
        return service.migliorIncasso(idAzienda);
    }
}
