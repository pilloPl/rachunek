package com.nbp.sorbnet3;


import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
class SaldaFacade {

    private final SaldaRepository saldaRepository;

    SaldaFacade(SaldaRepository saldaRepository) {
        this.saldaRepository = saldaRepository;
    }

    public void otworzSaldoNaKwote(Kwota saldoPoczatkowe, NumerRachunku numerRachunku) {
        saldaRepository.zapisz(new Saldo(numerRachunku, saldoPoczatkowe), numerRachunku);
    }

    public Rezultat przenies(NumerRachunku zasilany, NumerRachunku obciazany, Kwota kwota) {
        if(zasilany.jestZamkniety() || obciazany.jestZamkniety())
            return Rezultat.Nie_Przeniesiono;

        Saldo obciazane = saldaRepository.załaduj(obciazany);
        Saldo zasilane = saldaRepository.załaduj(zasilany);

        boolean rezult = obciazane.obciąż(kwota);
        if (rezult) {
            zasilane.uznaj(kwota);
            return Rezultat.Przeniesiono;
        }
        return Rezultat.Nie_Przeniesiono;
    }

    public boolean czyMaszKwote(NumerRachunku obciazany, Kwota blokady) {
        return saldaRepository.załaduj(obciazany).czyJestDostepnaKwota(blokady);
    }

    public Saldo get(NumerRachunku nr) {
        return saldaRepository.załaduj(nr);
    }

    public NumerRachunku zamknij(NumerRachunku rachunek) {
        Saldo saldo = saldaRepository.załaduj(rachunek);
        saldo.zamknij();
        return saldo.numerRachunku();
    }
}

@Repository
class SaldaRepository {

    private Map<NumerRachunku, Saldo> salda = new HashMap<>();

    public void zapisz(Saldo saldo, NumerRachunku numerRachunku) {
        salda.put(numerRachunku, saldo);
    }

    public Saldo załaduj(NumerRachunku numerRachunku) {
        return salda.get(numerRachunku);
    }
}


class Saldo {

    private NumerRachunku numerRachunku;
    private Kwota saldo;

    public Saldo(NumerRachunku numerRachunku, Kwota saldo) {
        this.numerRachunku = numerRachunku;
        this.saldo = saldo;
    }

    boolean uznaj(Kwota kwotaUznania) {
        saldo = saldo.dodaj(kwotaUznania);
        return true;
    }

    boolean obciąż(Kwota kwotaObciazenia) {
        if (saldo.jestMniejszeNiz(kwotaObciazenia)) {
            return false;
        }
        saldo = saldo.odejmij(kwotaObciazenia);
        return true;
    }


    public boolean czyJestDostepnaKwota(Kwota blokady) {
        return !saldo.jestMniejszeNiz(blokady);
    }

    public Kwota stan() {
        return saldo;
    }

    public NumerRachunku numerRachunku() {
        return numerRachunku;
    }

    public void zamknij() {
        this.numerRachunku = numerRachunku.zamknij();
    }
}