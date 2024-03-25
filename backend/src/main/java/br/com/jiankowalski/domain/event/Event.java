package br.com.jiankowalski.domain.event;

import br.com.jiankowalski.domain.AggregateRoot;
import br.com.jiankowalski.domain.exceptions.NotificationException;
import br.com.jiankowalski.domain.institution.InstitutionID;
import br.com.jiankowalski.domain.utils.InstantUtils;
import br.com.jiankowalski.domain.validation.ValidationHandler;
import br.com.jiankowalski.domain.validation.handler.Notification;

import java.time.Instant;

public class Event extends AggregateRoot<EventID> {

    private final InstitutionID institutionID;
    private final String name;
    private final Instant startAt;
    private final Instant finishAt;
    private final Instant createdAt;
    private boolean active;
    private Instant updatedAt;
    private Instant deletedAt;

    public Event(final EventID eventID,
                 final String aName,
                 final boolean isActive,
                 final InstitutionID institutionID,
                 final Instant startAt,
                 final Instant finishAt,
                 final Instant createdAt,
                 final Instant updatedAt,
                 final Instant deletedAt) {
        super(eventID);
        this.name = aName;
        this.institutionID = institutionID;
        this.active = isActive;
        this.startAt = startAt;
        this.finishAt = finishAt;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
        selfValidate();
    }

    public static Event newEvent(
            final String aName,
            final InstitutionID institutionID,
            final Instant startAt,
            final Instant finishAt

    ) {
        final var id = EventID.unique();
        final var now = InstantUtils.now();
        final var aEvent = new Event(id, aName, false, institutionID, startAt, finishAt, now, now, null);
        aEvent.updateStatus();
        return aEvent;
    }

    public static Event with(final EventID eventID,
                             final String aName,
                             final boolean isActive,
                             final InstitutionID institutionID,
                             final Instant startAt,
                             final Instant finishAt,
                             final Instant createdAt,
                             final Instant updatedAt,
                             final Instant deletedAt) {
        return new Event(eventID, aName, isActive, institutionID, startAt, finishAt, createdAt, updatedAt, deletedAt);
    }

    @Override
    public void validate(ValidationHandler handler) {
        new EventValidator(this, handler).validate();
    }

    private void selfValidate() {
        final var notification = Notification.create();
        validate(notification);

        if (notification.hasError()) {
            throw new NotificationException("Falha ao validar o evento", notification);
        }
    }

    public void updateStatus() {
        final var now = InstantUtils.now();
        this.active = InstantUtils.isAfterOrEquals(now, startAt) && InstantUtils.isAfterOrEquals(finishAt, now);
    }

    public InstitutionID getInstitutionID() {
        return institutionID;
    }

    public String getName() {
        return name;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getStartAt() {
        return startAt;
    }

    public Instant getFinishAt() {
        return finishAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }
}
