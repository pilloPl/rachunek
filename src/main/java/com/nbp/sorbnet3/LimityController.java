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
        NumerRachunku z2 = NumerRachunku.generuj();
        System.out.println(z);
        NumerRachunku na = NumerRachunku.generuj();
        NumerRachunku na2 = NumerRachunku.generuj();

        Kwota pln = Kwota.PLN(100);
        Kwota pln2 = Kwota.PLN(200);
        Kwota pln3 = Kwota.PLN(300);
        Kwota pln4 = Kwota.PLN(400);
        this.limityFacade.ustawLimit(z, na, pln);
        this.limityFacade.ustawLimit(z, na2, pln2);
        this.limityFacade.ustawLimit(z2, na, pln3);
        this.limityFacade.ustawLimit(z2, na2, pln4);
    }

    @GetMapping(path = "/limity/{nr}")
    ResponseEntity<List<LimityDto>> pokazLimity(@PathVariable UUID nr) {
        Map<NumerRachunku, Limit> limitMap = limityFacade.pobierzLimityDlaRachunku(nr);
        return ResponseEntity.ok(convert(nr, limitMap));
    }

    @GetMapping(path = "/limity")
    ResponseEntity<List<LimityDto>> pokazWszystkieLimity() {
        Map<NumerRachunku, Map<NumerRachunku, Limit>> mapa = limityFacade.pobierzWszystkieLimity();
        return ResponseEntity.ok(convert(mapa));
    }

    @PostMapping(path = "/limity/")
    ResponseEntity<LimityDto> ustawLimit(@RequestBody UstawLimitDto ustawLimitDto) {
        return null;
    }

    @PostMapping(path = "/limity/dodanie")
    ResponseEntity<List<LimityDto>> dodajLimit(@RequestParam("z") String z, @RequestParam("na") String na, @RequestParam("kwota") int kwota) {
        limityFacade.ustawLimit(new NumerRachunku(UUID.fromString(z)), new NumerRachunku(UUID.fromString(na)), Kwota.PLN(kwota));
        Map<NumerRachunku, Map<NumerRachunku, Limit>> mapa = limityFacade.pobierzWszystkieLimity();
        return ResponseEntity.ok(convert(mapa));
    }

    private List<LimityDto> convert(Map<NumerRachunku, Map<NumerRachunku, Limit>> mapa) {
        List<LimityDto> lista = new ArrayList<>();
        for(NumerRachunku nr : mapa.keySet()) {
            List<LimityDto> limity = convert(nr.getId(), mapa.get(nr));
            lista.addAll(limity);
        }
        return lista;
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
