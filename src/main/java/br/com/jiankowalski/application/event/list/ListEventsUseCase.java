package br.com.jiankowalski.application.event.list;

import br.com.jiankowalski.application.UseCase;
import br.com.jiankowalski.domain.pagination.Pagination;
import br.com.jiankowalski.domain.pagination.SearchQuery;

public abstract class ListEventsUseCase
        implements UseCase<SearchQuery, Pagination<EventListOutput>> {
}
