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

    public NumerRachunku otworzNowyRachunek(Kwota pln) {
        //otworz saldo
        //populuj widok
        return null;
    }

    public Rezultat przenies(NumerRachunku zasilany, NumerRachunku obciazany, Kwota pln) {
        //sprawdz blokade
        //sprawdz limit
        //sprawdz saldo
        //zmien saldo
        //zmien limit
        //populuj widok
        return null;
    }

    public void zamknij(NumerRachunku rachunek) {
        //zamknij saldo
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

    }

    public void zalozBlokade(NumerRachunku rachunek, Kwota pln) {

    }
}

enum Rezultat {
    Przeniesiono, Nie_Przeniesiono
}