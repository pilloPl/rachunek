package com.nbp.sorbnet3;


import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
class BlokadyFacade {

    private final BlokadyRepository blokadyRepository;

    BlokadyFacade(BlokadyRepository blokadyRepository) {
        this.blokadyRepository = blokadyRepository;
    }

    public void zalozBlokade(NumerRachunku numer, Kwota kwota) {
        blokadyRepository.zalozBlokade(numer, kwota);
    }

    public void zdejmijBlokade(NumerRachunku numer, Kwota kwota) {
        blokadyRepository.zmniejszBlokade(numer, kwota);
    }

    public boolean sprawdzBlokade(NumerRachunku numer, Kwota kwota) {
        Kwota kwotaBlokady = blokadyRepository.pobierzBlokade(numer);
        return kwotaBlokady == null || kwota.jestMniejszeNiz(kwotaBlokady);
    }

}


@Repository
class BlokadyRepository {

    Map<NumerRachunku, Kwota> blokady = new HashMap<>();

    public void zalozBlokade(NumerRachunku numer, Kwota kwotaBlokady) {
        blokady.put(numer, kwotaBlokady);
    }

    public void zmniejszBlokade(NumerRachunku numer, Kwota kwotaZmniejszenia) {
        Kwota kwotaBlokady = blokady.get(numer);
        Kwota nowaKwotaBlokady = kwotaBlokady.odejmij(kwotaZmniejszenia);
        blokady.put(numer, nowaKwotaBlokady);
        if (nowaKwotaBlokady.isZero()) {
            zdejmijBlokade(numer);
        }
    }

    public void zdejmijBlokade(NumerRachunku numer) {
        blokady.remove(numer);
    }

    public Kwota pobierzBlokade(NumerRachunku numer) {
        return blokady.get(numer);
    }

}