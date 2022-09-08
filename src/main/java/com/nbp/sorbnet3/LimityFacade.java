package com.nbp.sorbnet3;


import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Service
class LimityFacade {
    private final LimityRepository limityRepository;

    LimityFacade(LimityRepository limityRepository) {
        this.limityRepository = limityRepository;
    }

    public void ustawLimit(NumerRachunku z, NumerRachunku na, Kwota pln) {
        limityRepository.zapisz(new Limit(z, na, pln), z, na);
    }

    public void zdejmijLimit(NumerRachunku z, NumerRachunku na) {
        Limit limit = limityRepository.załaduj(z, na);
        limit.zdejmijLimit();
    }

    public void zwiekszLimit(NumerRachunku z, NumerRachunku na, Kwota pln) {
        Limit limit = limityRepository.załaduj(z, na);
        limit.zwiekszLimit(pln);
    }

    public void zmniejszLimit(NumerRachunku z, NumerRachunku na, Kwota pln) {
        Limit limit = limityRepository.załaduj(z, na);
        if (limit != null) {
            limit.zmniejszLimit(pln);
        }
    }

    public boolean sprawdzLimit(NumerRachunku z, NumerRachunku na, Kwota kwota) {
        Limit limit = limityRepository.załaduj(z, na);
        if (limit == null) {
            return true;
        }
        return limit.sprawdzLimit(kwota);
    }
}

@Repository
class LimityRepository {
    Map<NumerRachunku, Map<NumerRachunku, Limit>> limity = new HashMap<>();

    public void zapisz(Limit limit, NumerRachunku numerRachunkuZ, NumerRachunku numerRachunkuNa) {
        Map<NumerRachunku, Limit> limityZ = limity.get(numerRachunkuZ);
        if (limityZ == null) {
            limityZ = new HashMap<>();
            limity.put(numerRachunkuZ, limityZ);
        }
        limity.get(numerRachunkuZ).put(numerRachunkuNa, limit);
    }

    public Limit załaduj(NumerRachunku numerRachunkuZ, NumerRachunku numerRachunkuNa) {
        Map<NumerRachunku, Limit> numerRachunkuLimitMap = limity.get(numerRachunkuZ);
        if (numerRachunkuLimitMap != null) {
            return numerRachunkuLimitMap.get(numerRachunkuNa);
        }
        return null;
    }

    public Map<NumerRachunku, Limit> pobierzLimity(UUID nrRachunku) {
        return limity.get(new NumerRachunku(nrRachunku));
    }
}

class Limit {

    private NumerRachunku numerRachunkuZ;
    private NumerRachunku numerRachunkuNa;
    private Kwota limit;

    public Limit(NumerRachunku numerRachunkuZ, NumerRachunku numerRachunkuNa, Kwota limit) {
        this.numerRachunkuZ = numerRachunkuZ;
        this.numerRachunkuNa = numerRachunkuNa;
        this.limit = limit;
    }

    public void ustawLimit(Kwota pln) {
        limit = pln;
    }

    public void zdejmijLimit() {
        limit = Kwota.PLN(0);
    }

    public void zwiekszLimit(Kwota pln) {
        limit = limit.dodaj(pln);
    }

    public void zmniejszLimit(Kwota pln) {
        limit = limit.odejmij(pln);
    }

    public boolean sprawdzLimit(Kwota kwota) {
        return !limit.jestMniejszeNiz(kwota);
    }

    public int zwrocLimit() {
        return limit.getAmount();
    }
}
