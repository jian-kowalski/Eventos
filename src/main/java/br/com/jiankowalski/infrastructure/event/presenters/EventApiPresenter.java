package br.com.jiankowalski.infrastructure.event.presenters;

import br.com.jiankowalski.application.event.list.EventListOutput;
import br.com.jiankowalski.domain.utils.InstantUtils;
import br.com.jiankowalski.infrastructure.event.models.EventListResponse;

public interface EventApiPresenter {


    static EventListResponse present(final EventListOutput output) {
        return new EventListResponse(
                output.id(),
                output.name(),
                output.isActive(),
                InstantUtils.asString(output.startAt()),
                InstantUtils.asString(output.finishAt()),
                InstantUtils.asString(output.createdAt())
        );
    }
}
