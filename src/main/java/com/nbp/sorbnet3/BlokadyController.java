package com.nbp.sorbnet3;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
class BlokadyController {

    private final BlokadyRepository blokadyRepository;

    BlokadyController(BlokadyRepository blokadyRepository) {
        this.blokadyRepository = blokadyRepository;
        NumerRachunku generuj = NumerRachunku.generuj();
        System.out.println(generuj);
        blokadyRepository.zalozBlokade(generuj, Kwota.PLN(100));
    }

    @GetMapping(path = "/blokady/{nr}")
    ResponseEntity<BlokadyDto> getBlokady(@PathVariable UUID nr) {
        Kwota blokada = blokadyRepository.pobierzBlokade(new NumerRachunku(nr));
        BlokadyDto blokadyDto = new BlokadyDto(nr, blokada.getAmount());
        return ResponseEntity.ok(blokadyDto);
    }



}


class BlokadyDto {

    public BlokadyDto(UUID numerRachunku, int kwota) {
        this.numerRachunku = numerRachunku;
        this.kwota = kwota;
    }

    public UUID numerRachunku;
    public int kwota;
}