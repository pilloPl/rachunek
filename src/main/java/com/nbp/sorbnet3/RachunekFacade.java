package com.nbp.sorbnet3;


import org.springframework.stereotype.Service;

@Service
public class RachunekFacade {

    private final BlokadyFacade blokadyFacade;
    private final LimityFacade limityFacade;
    private final SaldaFacade saldaFacade;

    public RachunekFacade(BlokadyFacade blokadyFacade, LimityFacade limityFacade, SaldaFacade saldaFacade) {
        this.blokadyFacade = blokadyFacade;
        this.limityFacade = limityFacade;
        this.saldaFacade = saldaFacade;
    }

    public NumerRachunku otworzNowyRachunek() {
        return otworzNowyRachunek(Kwota.PLN(0));
    }

    public NumerRachunku otworzNowyRachunek(Kwota saldo) {

        NumerRachunku numerRachunku = NumerRachunku.generuj();
        numerRachunku = numerRachunku.otworz();
        saldaFacade.otworzSaldoNaKwote(saldo, numerRachunku);
        //otworz saldo
        //populuj widok
        return numerRachunku;
    }

    public Rezultat przenies(NumerRachunku zasilany, NumerRachunku obciazany, Kwota kwota) {
        Kwota blokady = blokadyFacade.dajBlokade(obciazany);
        if (!saldaFacade.czyMaszKwote(obciazany, blokady.dodaj(kwota))) {
            return Rezultat.Nie_Przeniesiono;
        }
        boolean limitOK = limityFacade.sprawdzLimit(obciazany, zasilany, kwota);
        if (!limitOK) {
            return Rezultat.Nie_Przeniesiono;
        } else {
            limityFacade.zmniejszLimit(obciazany, zasilany, kwota);
        }
        Rezultat rezultat = saldaFacade.przenies(zasilany, obciazany, kwota);
        return rezultat;
    }

    public NumerRachunku zamknij(NumerRachunku rachunek) {
        return saldaFacade.zamknij(rachunek);
    }

    public void ustawLimit(NumerRachunku z, NumerRachunku na, Kwota pln) {
        limityFacade.ustawLimit(z, na, pln);

    }

    public void zdejmijLimit(NumerRachunku z, NumerRachunku na) {
        limityFacade.zdejmijLimit(z, na);
    }

    public void zwiekszLimit(NumerRachunku z, NumerRachunku na, Kwota pln) {
        limityFacade.zwiekszLimit(z, na, pln);

    }

    public void zmniejszLimit(NumerRachunku z, NumerRachunku na, Kwota pln) {
        limityFacade.zmniejszLimit(z, na, pln);
    }

    public void zdejmijBlokade(NumerRachunku z, Kwota pln) {
        blokadyFacade.zdejmijBlokade(z, pln);
    }

    public void zalozBlokade(NumerRachunku rachunek, Kwota pln) {
        blokadyFacade.zalozBlokade(rachunek, pln);
    }

    public Saldo zaladuj(NumerRachunku nr) {
        return saldaFacade.get(nr);
    }
}

enum Rezultat {
    Przeniesiono, Nie_Przeniesiono
}

enum Status {
    Otwarty, Zamkniety, Utworzony;
}