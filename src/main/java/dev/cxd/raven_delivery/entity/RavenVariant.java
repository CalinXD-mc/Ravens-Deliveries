package dev.cxd.raven_delivery.entity;

import java.util.Arrays;
import java.util.Comparator;

public enum  RavenVariant {
    DARK(0),
    SEA_GREEN(1),
    ALBINO(2);

    private static final RavenVariant[] BY_ID = Arrays.stream(values()).sorted(Comparator.
            comparingInt(RavenVariant::getId)).toArray(RavenVariant[]::new);
    private final int id;

    RavenVariant(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public static RavenVariant byId(int id) {
        return BY_ID[id % BY_ID.length];
    }
}
