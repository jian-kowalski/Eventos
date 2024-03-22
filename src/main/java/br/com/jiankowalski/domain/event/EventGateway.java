package br.com.jiankowalski.domain.event;

import br.com.jiankowalski.domain.pagination.Pagination;
import br.com.jiankowalski.domain.pagination.SearchQuery;

public interface EventGateway {

    Event create(Event aEvent);

    Event update(Event aEvent);

    Pagination<Event> findAll(SearchQuery aQuery);
}
