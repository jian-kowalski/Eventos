package br.com.jiankowalski.application.institution.create;

import br.com.jiankowalski.domain.exceptions.NotificationException;
import br.com.jiankowalski.domain.institution.Institution;
import br.com.jiankowalski.domain.institution.InstitutionGateway;
import br.com.jiankowalski.domain.validation.handler.Notification;

import java.util.Objects;

public class DefaultCreateInstitutionUseCase extends CreateInstitutionUseCase {

    private final InstitutionGateway institutionGateway;

    public DefaultCreateInstitutionUseCase(final InstitutionGateway institutionGateway) {
        this.institutionGateway = Objects.requireNonNull(institutionGateway);
    }

    @Override
    public CreateInstitutionOutput execute(final CreateInstitutionCommand aCommand) {
        final var aName = aCommand.name();
        final var aType = aCommand.type();
        final var notification = Notification.create();

        final var aInstitution = notification.validate(() -> Institution.newInstitution(aName, aType));

        if (notification.hasError()) {
            throw new NotificationException("Não foi possível criar a instituição", notification);
        }

        return CreateInstitutionOutput.from(this.institutionGateway.create(aInstitution).getId().getValue());
    }
}
