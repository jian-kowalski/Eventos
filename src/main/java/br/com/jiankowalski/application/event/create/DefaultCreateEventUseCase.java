package br.com.jiankowalski.application.event.create;

import br.com.jiankowalski.domain.event.Event;
import br.com.jiankowalski.domain.event.EventGateway;
import br.com.jiankowalski.domain.exceptions.NotificationException;
import br.com.jiankowalski.domain.institution.InstitutionGateway;
import br.com.jiankowalski.domain.institution.InstitutionID;
import br.com.jiankowalski.domain.validation.Error;
import br.com.jiankowalski.domain.validation.ValidationHandler;
import br.com.jiankowalski.domain.validation.handler.Notification;

import java.util.Objects;

public class DefaultCreateEventUseCase extends CreateEventUseCase {

    private final InstitutionGateway institutionGateway;
    private final EventGateway eventGateway;

    public DefaultCreateEventUseCase(final InstitutionGateway institutionGateway,
                                     final EventGateway eventGateway) {
        this.institutionGateway = Objects.requireNonNull(institutionGateway);
        this.eventGateway = Objects.requireNonNull(eventGateway);
    }


    @Override
    public CreateEventOutput execute(final CreateEventCommand aCommand) {
        final var aName = aCommand.aName();
        final var aInstitution = InstitutionID.from(aCommand.institutionID());
        final var aStartAt = aCommand.startAt();
        final var aFinishAt = aCommand.finishAt();
        final var notification = Notification.create();
        notification.append(validateInstitutionId(aInstitution));
        final var aEvent = notification.validate(() -> Event.newEvent(aName, aInstitution, aStartAt, aFinishAt));
        if (notification.hasError()) {
            throw new NotificationException("Não foi possível criar a instituição", notification);
        }
        return CreateEventOutput.from(this.eventGateway.create(aEvent));
    }

    private ValidationHandler validateInstitutionId(final InstitutionID aID) {
        final var notification = Notification.create();
        final var exists = this.institutionGateway.existsById(aID);
        if (exists) {
            return notification;
        }
        return notification.append(new Error("Não foi encontrada a instituição com o id: " + aID));
    }
}
