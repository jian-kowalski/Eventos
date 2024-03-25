package br.com.jiankowalski.application.event.list;

import br.com.jiankowalski.application.UseCase;
import br.com.jiankowalski.domain.event.EventSearchQuery;
import br.com.jiankowalski.domain.pagination.Pagination;

public abstract class ListEventsUseCase
        implements UseCase<EventSearchQuery, Pagination<EventListOutput>> {
}
