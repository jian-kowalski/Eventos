package br.com.jiankowalski.domain.institution;

public interface InstitutionGateway {

    Institution create(Institution institution);

    boolean existsById(InstitutionID id);
}
