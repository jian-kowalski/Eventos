package br.com.jiankowalski.infrastructure.event;

import br.com.jiankowalski.domain.event.Event;
import br.com.jiankowalski.domain.event.EventGateway;
import br.com.jiankowalski.infrastructure.event.persistence.EventJpaEntity;
import br.com.jiankowalski.infrastructure.event.persistence.EventRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Objects;

@ApplicationScoped
public class EventPostgresGateway implements EventGateway {

    private final EventRepository eventRepository;

    public EventPostgresGateway(final EventRepository eventRepository) {
        this.eventRepository = Objects.requireNonNull(eventRepository);
    }

    @Override
    public Event create(final Event aEvent) {
        return save(aEvent);
    }

    @Transactional
    private Event save(final Event aEvent) {
        final var eventJpaEntity = EventJpaEntity.from(aEvent);
        eventRepository.persistAndFlush(eventJpaEntity);
        return eventJpaEntity.toAggregate();
    }

    @Override
    public Event update(final Event aEvent) {
        return save(aEvent);
    }
}
