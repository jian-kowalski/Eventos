package br.com.jiankowalski.application.event.create;

import br.com.jiankowalski.domain.event.Event;

public record CreateEventOutput(String id) {
    public static CreateEventOutput from(final Event event) {
        return new CreateEventOutput(event.getId().getValue());
    }
}
