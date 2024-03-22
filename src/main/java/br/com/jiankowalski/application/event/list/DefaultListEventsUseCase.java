package br.com.jiankowalski.application.event.list;

import br.com.jiankowalski.domain.event.EventGateway;
import br.com.jiankowalski.domain.pagination.Pagination;
import br.com.jiankowalski.domain.pagination.SearchQuery;

import java.util.Objects;

public class DefaultListEventsUseCase extends ListEventsUseCase {

    private final EventGateway eventGateway;

    public DefaultListEventsUseCase(final EventGateway eventGateway) {
        this.eventGateway = Objects.requireNonNull(eventGateway);
    }

    @Override
    public Pagination<EventListOutput> execute(final SearchQuery aQuery) {
        return this.eventGateway.findAll(aQuery)
                .map(EventListOutput::from);
    }
}
