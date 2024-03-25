package br.com.jiankowalski.infrastructure.configuration.usecases;

import br.com.jiankowalski.application.institution.create.CreateInstitutionUseCase;
import br.com.jiankowalski.application.institution.create.DefaultCreateInstitutionUseCase;
import br.com.jiankowalski.application.institution.list.DefaultListInstitutionsUseCase;
import br.com.jiankowalski.application.institution.list.ListInstitutionUseCase;
import br.com.jiankowalski.domain.institution.InstitutionGateway;
import jakarta.enterprise.context.ApplicationScoped;

public class InstitutionUseCaseConfig {

    private final InstitutionGateway institutionGateway;

    public InstitutionUseCaseConfig(
            final InstitutionGateway institutionGateway) {
        this.institutionGateway = institutionGateway;
    }

    @ApplicationScoped
    public CreateInstitutionUseCase createInstitutionUseCase() {
        return new DefaultCreateInstitutionUseCase(institutionGateway);
    }

    @ApplicationScoped
    public ListInstitutionUseCase listInstitutionsUseCase() {
        return new DefaultListInstitutionsUseCase(institutionGateway);
    }
}
