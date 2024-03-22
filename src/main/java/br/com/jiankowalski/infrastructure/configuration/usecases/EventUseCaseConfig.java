package br.com.jiankowalski.infrastructure.configuration.usecases;

import br.com.jiankowalski.application.event.create.CreateEventUseCase;
import br.com.jiankowalski.application.event.create.DefaultCreateEventUseCase;
import br.com.jiankowalski.application.event.list.DefaultListEventsUseCase;
import br.com.jiankowalski.application.event.list.ListEventsUseCase;
import br.com.jiankowalski.domain.event.EventGateway;
import br.com.jiankowalski.domain.institution.InstitutionGateway;
import jakarta.enterprise.context.ApplicationScoped;

public class EventUseCaseConfig {

    private final EventGateway eventGateway;

    private final InstitutionGateway institutionGateway;

    public EventUseCaseConfig(final EventGateway eventGateway,
                              final InstitutionGateway institutionGateway) {
        this.eventGateway = eventGateway;
        this.institutionGateway = institutionGateway;
    }

    @ApplicationScoped
    public CreateEventUseCase createEventUseCase() {
        return new DefaultCreateEventUseCase(institutionGateway, eventGateway);
    }

    @ApplicationScoped
    public ListEventsUseCase listEventsUseCase() {
        return new DefaultListEventsUseCase(eventGateway);
    }
}
