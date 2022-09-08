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
        //otworz saldo
        //populuj widok
        return null;
    }

    public NumerRachunku otworzNowyRachunek(Kwota saldo) {
        NumerRachunku numerRachunku = NumerRachunku.generuj();
        numerRachunku.otworz();
        saldaFacade.otworzSaldoNaKwote(saldo, numerRachunku);
        //otworz saldo
        //populuj widok
        return numerRachunku;
    }

    public Rezultat przenies(NumerRachunku zasilany, NumerRachunku obciazany, Kwota kwota) {
        //sprawdz blokade BFG
        if (!blokadyFacade.sprawdzBlokade(obciazany, kwota)) {
            return Rezultat.Nie_Przeniesiono;
        }

        //sprawdz limit

        //zmien limit
        //populuj widok

        Rezultat rezultat = saldaFacade.przenies(zasilany, obciazany, kwota);
        return rezultat;
    }

    public void zamknij(NumerRachunku rachunek) {
        rachunek.zamknij();//zamknij saldo
    }

    public void ustawLimit(NumerRachunku z, NumerRachunku na, Kwota pln) {

    }

    public void zdejmijLimit(NumerRachunku z, NumerRachunku na) {

    }

    public void zwiekszLimit(NumerRachunku z, NumerRachunku na, Kwota pln) {
    }

    public void zmniejszLimit(NumerRachunku z, NumerRachunku na, Kwota pln) {

    }

    public void zdejmijBlokade(NumerRachunku z, Kwota pln) {
        blokadyFacade.zdejmijBlokade(z, pln);
    }

    public void zalozBlokade(NumerRachunku rachunek, Kwota pln) {
        blokadyFacade.zalozBlokade(rachunek, pln);
    }
}

enum Rezultat {
    Przeniesiono, Nie_Przeniesiono
}

enum Status {
    Otwarty, Zamkniety;
}