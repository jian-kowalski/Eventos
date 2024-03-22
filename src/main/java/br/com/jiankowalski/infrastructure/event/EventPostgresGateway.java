package br.com.jiankowalski.infrastructure.event;

import br.com.jiankowalski.domain.event.Event;
import br.com.jiankowalski.domain.event.EventGateway;
import br.com.jiankowalski.domain.pagination.Pagination;
import br.com.jiankowalski.domain.pagination.SearchQuery;
import br.com.jiankowalski.infrastructure.event.persistence.EventJpaEntity;
import br.com.jiankowalski.infrastructure.event.persistence.EventRepository;
import io.quarkus.panache.common.Page;
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
    @Transactional
    public Event create(final Event aEvent) {
        return save(aEvent);
    }


    private Event save(final Event aEvent) {
        final var eventJpaEntity = EventJpaEntity.from(aEvent);
        eventRepository.persistAndFlush(eventJpaEntity);
        return eventJpaEntity.toAggregate();
    }

    @Override
    @Transactional
    public Event update(final Event aEvent) {
        return save(aEvent);
    }

    @Override
    public Pagination<Event> findAll(SearchQuery aQuery) {
        final var page = Page.of(aQuery.page(), aQuery.perPage());
        final var pageResult = eventRepository.findAll()
                .page(page);
        return new Pagination<>(
                pageResult.page().index,
                pageResult.page().size,
                pageResult.pageCount(),
                pageResult.stream().map(EventJpaEntity::toAggregate).toList()
        );

    }
}
