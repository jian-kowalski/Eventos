package br.com.jiankowalski.domain.event;

import br.com.jiankowalski.domain.Identifier;
import br.com.jiankowalski.domain.utils.IdUtils;

import java.util.Objects;

public class EventID extends Identifier {
    private final String value;

    private EventID(final String value) {
        this.value = Objects.requireNonNull(value);
    }

    public static EventID unique() {
        return EventID.from(IdUtils.uuid());
    }

    public static EventID from(final String anId) {
        return new EventID(anId);
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        final EventID that = (EventID) o;
        return getValue().equals(that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }

    @Override
    public String toString() {
        return "EventID{" +
                "value='" + value + '\'' +
                '}';
    }
}