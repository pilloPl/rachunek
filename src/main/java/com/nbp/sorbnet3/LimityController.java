package com.nbp.sorbnet3;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
class LimityController {

    private final LimityFacade limityFacade;

    LimityController(LimityFacade limityFacade) {
        this.limityFacade = limityFacade;
        NumerRachunku z = NumerRachunku.generuj();
        System.out.println(z);
        NumerRachunku na = NumerRachunku.generuj();
        Kwota pln = Kwota.PLN(100);
        this.limityFacade.ustawLimit(z, na, pln);
    }

    @GetMapping(path = "/limity/{nr}")
    ResponseEntity<List<LimityDto>> pokazLimity(@PathVariable UUID nr) {
        Map<NumerRachunku, Limit> limitMap = limityFacade.pobierzLimityDlaRachunku(nr);
        return ResponseEntity.ok(convert(nr, limitMap));
    }

    @PostMapping(path = "/limity/")
    ResponseEntity<LimityDto> ustawLimit(@RequestBody UstawLimitDto ustawLimitDto) {
        return null;
    }

    private List<LimityDto> convert(UUID zrodlowy, Map<NumerRachunku, Limit> limityMap) {
        List<LimityDto> lista = new ArrayList<>();
        for(NumerRachunku nr : limityMap.keySet()) {
            LimityDto limit = new LimityDto(zrodlowy, nr.getId(), limityMap.get(nr).zwrocLimit());
            lista.add(limit);
        }
        return lista;
    }
}

class LimityDto {
    public UUID rachunekZrodlowy;
    public UUID rachunekDocelowy;
    public int kwota;

    public LimityDto() {
    }

    public LimityDto(UUID rachunekZrodlowy, UUID rachunekDocelowy, int kwota) {
        this.rachunekZrodlowy = rachunekZrodlowy;
        this.rachunekDocelowy = rachunekDocelowy;
        this.kwota = kwota;
    }
}

class UstawLimitDto {
    public UUID zRachunku;
    public UUID naRachunek;
    public int kwota;
}
