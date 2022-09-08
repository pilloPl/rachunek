package com.nbp.sorbnet3;

import java.util.Objects;
import java.util.UUID;

public class NumerRachunku {

    static NumerRachunku generuj() {
        return new NumerRachunku(UUID.randomUUID());
    }

    private final UUID nrb;
    private final Status status;

    public NumerRachunku(UUID numer) {
        this.nrb = numer;
        status = Status.Utworzony;
    }

    public NumerRachunku(UUID numer, Status status) {
        this.nrb = numer;
        this.status = status;
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

    public NumerRachunku zamknij() {

        return new NumerRachunku(nrb, Status.Zamkniety);
    }

    public NumerRachunku otworz() {
        return new NumerRachunku(nrb, Status.Otwarty);
    }

    public boolean jestZamkniety() {
        return Status.Zamkniety.equals(status);
    }

    @Override
    public String toString() {
        return "NumerRachunku{" +
                "nrb=" + nrb +
                ", status=" + status +
                '}';
    }

    public UUID getId() {
        return nrb;
    }

    public String getStatus() {
        return status.toString();
    }
}
