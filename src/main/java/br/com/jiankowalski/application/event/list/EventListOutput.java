package br.com.jiankowalski.application.event.list;

import br.com.jiankowalski.domain.event.Event;

import java.time.Instant;

public record EventListOutput(
        String id,
        String name,
        boolean isActive,
        Instant startAt,
        Instant finishAt,
        Instant createdAt
) {

    public static EventListOutput from(final Event aEvent) {
        return new EventListOutput(
                aEvent.getId().getValue(),
                aEvent.getName(),
                aEvent.isActive(),
                aEvent.getStartAt(),
                aEvent.getFinishAt(),
                aEvent.getCreatedAt()
        );
    }
}
