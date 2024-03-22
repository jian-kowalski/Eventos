package br.com.jiankowalski.infrastructure.institution;

import br.com.jiankowalski.domain.institution.Institution;
import br.com.jiankowalski.domain.institution.InstitutionGateway;
import br.com.jiankowalski.domain.institution.InstitutionID;
import br.com.jiankowalski.infrastructure.institution.persistence.InstitutionJpaEntity;
import br.com.jiankowalski.infrastructure.institution.persistence.InstitutionRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;

import java.util.Objects;

@ApplicationScoped
public class InstitutionPostgresGateway implements InstitutionGateway {

    private final InstitutionRepository institutionRepository;

    public InstitutionPostgresGateway(final InstitutionRepository institutionRepository) {
        this.institutionRepository = Objects.requireNonNull(institutionRepository);
    }


    @Override
    @Transactional
    public Institution create(final Institution institution) {
        final var institutionJpaEntity = InstitutionJpaEntity.from(institution);
        institutionRepository.persistAndFlush(institutionJpaEntity);
        return institutionJpaEntity.toAggregate();
    }

    @Override
    public boolean existsById(final InstitutionID id) {
        return institutionRepository.findByIdOptional(id.getValue()).isPresent();
    }
}
