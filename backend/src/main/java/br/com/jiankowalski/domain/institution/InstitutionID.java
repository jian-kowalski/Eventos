package br.com.jiankowalski.domain.institution;

import br.com.jiankowalski.domain.Identifier;
import br.com.jiankowalski.domain.utils.IdUtils;

import java.util.Objects;

public class InstitutionID extends Identifier {
    private final String value;

    private InstitutionID(final String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static InstitutionID unique() {
        return InstitutionID.from(IdUtils.uuid());
    }

    public static InstitutionID from(final String anId) {
        return new InstitutionID(anId);
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final InstitutionID that = (InstitutionID) o;
        return getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }

    @Override
    public String toString() {
        return "InstitutionID{" +
                "value='" + value + '\'' +
                '}';
    }
}