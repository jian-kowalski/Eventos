package br.com.jiankowalski.application.event.list;

import br.com.jiankowalski.domain.event.EventGateway;
import br.com.jiankowalski.domain.event.EventSearchQuery;
import br.com.jiankowalski.domain.pagination.Pagination;

import java.util.Objects;

public class DefaultListEventsUseCase extends ListEventsUseCase {

    private final EventGateway eventGateway;

    public DefaultListEventsUseCase(final EventGateway eventGateway) {
        this.eventGateway = Objects.requireNonNull(eventGateway);
    }

    @Override
    public Pagination<EventListOutput> execute(final EventSearchQuery aQuery) {
        return this.eventGateway.findAll(aQuery)
                .map(EventListOutput::from);
    }
}
