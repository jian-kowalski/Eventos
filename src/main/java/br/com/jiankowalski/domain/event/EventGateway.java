package br.com.jiankowalski.domain.event;

import br.com.jiankowalski.domain.pagination.Pagination;

import java.util.Set;

public interface EventGateway {

    Event create(Event aEvent);

    void update(Set<Event> aEvents);

    Pagination<Event> findAll(EventSearchQuery aQuery);

    Set<Event> findProcessToday(int page, int perPage);
}
