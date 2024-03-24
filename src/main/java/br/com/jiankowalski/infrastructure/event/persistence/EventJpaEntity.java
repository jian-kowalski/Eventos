package br.com.jiankowalski.infrastructure.event.persistence;

import br.com.jiankowalski.domain.event.Event;
import br.com.jiankowalski.domain.event.EventID;
import br.com.jiankowalski.domain.institution.InstitutionID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;
import java.util.Objects;

@Entity
@Table(name = "events")
public class EventJpaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "start_at", nullable = false)
    private Instant startAt;

    @Column(name = "finish_at", nullable = false)
    private Instant finishAt;

    @Column(name = "institution_id", nullable = false)
    private String institutionId;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;

    @Column(name = "deleted_at")
    private Instant deletedAt;


    public EventJpaEntity() {
    }

    private EventJpaEntity(
            final String id,
            final String name,
            final boolean active,
            final Instant startAt,
            final Instant finishAt,
            final String institutionId,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        this.id = id;
        this.name = name;
        this.active = active;
        this.startAt = startAt;
        this.finishAt = finishAt;
        this.institutionId = institutionId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static EventJpaEntity from(final Event aEvent) {
        return new EventJpaEntity(
                aEvent.getId().getValue(),
                aEvent.getName(),
                aEvent.isActive(),
                aEvent.getStartAt(),
                aEvent.getFinishAt(),
                aEvent.getInstitutionID().getValue(),
                aEvent.getCreatedAt(),
                aEvent.getUpdatedAt(),
                aEvent.getDeletedAt()
        );
    }

    public Event toAggregate() {
        return Event.with(
                EventID.from(getId()),
                getName(),
                isActive(),
                InstitutionID.from(getInstitutionId()),
                getStartAt(),
                getFinishAt(),
                getCreatedAt(),
                getUpdatedAt(),
                getDeletedAt()
        );
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public Instant getStartAt() {
        return startAt;
    }

    public void setStartAt(Instant startAt) {
        this.startAt = startAt;
    }

    public Instant getFinishAt() {
        return finishAt;
    }

    public void setFinishAt(Instant finishAt) {
        this.finishAt = finishAt;
    }

    public String getInstitutionId() {
        return institutionId;
    }

    public void setInstitutionId(String institutionId) {
        this.institutionId = institutionId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventJpaEntity that = (EventJpaEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
