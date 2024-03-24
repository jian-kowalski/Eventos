package br.com.jiankowalski.infrastructure.event;

import br.com.jiankowalski.domain.event.Event;
import br.com.jiankowalski.domain.event.EventGateway;
import br.com.jiankowalski.domain.event.EventSearchQuery;
import br.com.jiankowalski.domain.pagination.Pagination;
import br.com.jiankowalski.domain.utils.InstantUtils;
import br.com.jiankowalski.infrastructure.event.persistence.EventJpaEntity;
import br.com.jiankowalski.infrastructure.event.persistence.EventRepository;
import io.quarkus.panache.common.Page;
import io.quarkus.panache.common.Sort;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@ApplicationScoped
public class EventPostgresGateway implements EventGateway {

    private final EventRepository eventRepository;

    public EventPostgresGateway(final EventRepository eventRepository) {
        this.eventRepository = Objects.requireNonNull(eventRepository);
    }

    @Override
    @Transactional
    public Event create(final Event aEvent) {
        final var eventJpaEntity = EventJpaEntity.from(aEvent);
        eventRepository.persistAndFlush(eventJpaEntity);
        return eventJpaEntity.toAggregate();
    }

    @Override
    @Transactional
    public void update(Set<Event> aEvents) {
        final var entities = aEvents
                .stream()
                .map(EventJpaEntity::from)
                .collect(Collectors.toSet());
        entities.forEach(entity -> eventRepository.getEntityManager().merge(entity));
    }


    @Override
    public Pagination<Event> findAll(EventSearchQuery aQuery) {
        final var page = Page.of(aQuery.page(), aQuery.perPage());
        final var pageResult = eventRepository.find("institutionId",
                        Sort.by("startAt").and("active"),
                        aQuery.institution().getValue())
                .page(page);
        return new Pagination<>(
                pageResult.page().index,
                pageResult.page().size,
                pageResult.pageCount(),
                pageResult.stream().map(EventJpaEntity::toAggregate).toList()
        );

    }

    @Override
    public Set<Event> findProcessToday(final int page, final int perPage) {
        final var pag = Page.of(page, perPage);
        return eventRepository.find("WHERE ?1 between startAt and (finishAt + 1 day)",
                        Sort.by("createdAt"),
                        InstantUtils.now())
                .page(pag)
                .stream()
                .map(EventJpaEntity::toAggregate)
                .collect(Collectors.toSet());
    }


}
