package br.com.jiankowalski.infrastructure.institution.persistence;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class InstitutionRepository implements PanacheRepositoryBase<InstitutionJpaEntity, String> {

}
