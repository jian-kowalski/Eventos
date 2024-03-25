package br.com.jiankowalski.domain.institution;

import br.com.jiankowalski.domain.AggregateRoot;
import br.com.jiankowalski.domain.exceptions.NotificationException;
import br.com.jiankowalski.domain.validation.ValidationHandler;
import br.com.jiankowalski.domain.validation.handler.Notification;

import java.time.Instant;

public class Institution extends AggregateRoot<InstitutionID> {

    private final String name;
    private final InstitutionType type;
    private final Instant createdAt;

    private Institution(final InstitutionID institutionID,
                        final String name,
                        final InstitutionType type,
                        final Instant createdAt) {
        super(institutionID);
        this.name = name;
        this.type = type;
        this.createdAt = createdAt;
        selfValidate();
    }

    public static Institution newInstitution(
            final String name,
            final InstitutionType type
    ) {
        final var institutionID = InstitutionID.unique();
        final var now = Instant.now();
        return new Institution(institutionID, name, type, now);
    }

    public static Institution with(
            final InstitutionID institutionID,
            final String name,
            final InstitutionType type,
            final Instant createdAt
    ) {
        return new Institution(institutionID, name, type, createdAt);
    }

    @Override
    public void validate(ValidationHandler handler) {
        new InstitutionValidator(this, handler).validate();
    }

    private void selfValidate() {
        final var notification = Notification.create();
        validate(notification);

        if (notification.hasError()) {
            throw new NotificationException("Falha ao criar a instituição", notification);
        }
    }

    public String getName() {
        return name;
    }

    public InstitutionType getType() {
        return type;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
