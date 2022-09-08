package com.nbp.sorbnet3;

import java.util.Objects;

class Kwota {

    //wiadomo, ze nie int
    private final int amount;

    Kwota(int amount) {
        this.amount = amount;
    }

    public static Kwota PLN(int pln) {
        return new Kwota(pln);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Kwota money = (Kwota) o;
        return amount == money.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    public boolean jestMniejszeNiz(Kwota kwota) {
        return this.amount < kwota.amount;
    }

    public Kwota odejmij(Kwota kwota) {
        return Kwota.PLN(amount - kwota.amount);
    }

    public Kwota dodaj(Kwota kwota) {
        return Kwota.PLN(amount + kwota.amount);
    }
}
