package br.com.jiankowalski.domain.institution;

import java.util.Arrays;
import java.util.Optional;

public enum
InstitutionType {
    CENTRAL("Central"),
    COOPERATIVE("Cooperativa"),
    SINGULAR("Singular"),
    CONFEDERATION("Confederação");

    private final String desc;

    InstitutionType(String desc) {
        this.desc = desc;
    }

    public static Optional<InstitutionType> of(final String type) {
        return Arrays.stream(values())
                .filter(it -> it.name().equalsIgnoreCase(type))
                .findFirst();
    }

    public String description() {
        return desc;
    }
}
