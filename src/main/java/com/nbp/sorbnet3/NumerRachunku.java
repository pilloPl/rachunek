package com.nbp.sorbnet3;

import java.util.Objects;
import java.util.UUID;

public class NumerRachunku {

    static NumerRachunku newOne() {
        return new NumerRachunku(UUID.randomUUID());
    }

    private final UUID numer;

    public NumerRachunku(UUID numer) {
        this.numer = numer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NumerRachunku that = (NumerRachunku) o;
        return Objects.equals(numer, that.numer);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numer);
    }
}
