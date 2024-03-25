package br.com.jiankowalski.domain.institution;

import java.util.Set;

public interface InstitutionGateway {

    Institution create(Institution institution);

    boolean existsById(InstitutionID id);

    Set<Institution> findAll();
}
