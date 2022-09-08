package com.nbp.sorbnet3;

import java.util.Objects;
import java.util.UUID;

public class NumerRachunku {

    static NumerRachunku generuj() {
        return new NumerRachunku(UUID.randomUUID());
    }

    private final UUID nrb;
    private Status status;

    public NumerRachunku(UUID numer) {
        this.nrb = numer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumerRachunku that = (NumerRachunku) o;
        return Objects.equals(nrb, that.nrb);
    }

    @Override
    public int hashCode() {
        return Objects.hash(nrb);
    }

    public void zamknij() {
        this.status = Status.Zamkniety;
    }

    public void otworz() {
        this.status = Status.Otwarty;
    }

    public boolean jestZamkniety() {
        return Status.Zamkniety.equals(status);
    }
}
