package br.com.jiankowalski.infrastructure.institution.persistence;

import br.com.jiankowalski.domain.institution.Institution;
import br.com.jiankowalski.domain.institution.InstitutionID;
import br.com.jiankowalski.domain.institution.InstitutionType;
import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "institutions")
public class InstitutionJpaEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "institution_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private InstitutionType type;


    @Column(name = "created_at", nullable = false, columnDefinition = "DATETIME(6)")
    private Instant createdAt;


    public InstitutionJpaEntity() {
    }

    private InstitutionJpaEntity(
            final String id,
            final String name,
            final InstitutionType type,
            final Instant createdAt
    ) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.createdAt = createdAt;
    }

    public static InstitutionJpaEntity from(final Institution aInstitution) {
        return new InstitutionJpaEntity(
                aInstitution.getId().getValue(),
                aInstitution.getName(),
                aInstitution.getType(),
                aInstitution.getCreatedAt()
        );
    }

    public Institution toAggregate() {
        return Institution.with(
                InstitutionID.from(getId()),
                getName(),
                getType(),
                getCreatedAt()
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

    public InstitutionType getType() {
        return type;
    }

    public void setType(InstitutionType type) {
        this.type = type;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
