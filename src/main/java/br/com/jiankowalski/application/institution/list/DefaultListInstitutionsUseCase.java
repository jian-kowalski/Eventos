package br.com.jiankowalski.application.institution.list;

import br.com.jiankowalski.domain.institution.InstitutionGateway;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class DefaultListInstitutionsUseCase extends ListInstitutionUseCase {

    private final InstitutionGateway institutionGateway;

    public DefaultListInstitutionsUseCase(final InstitutionGateway institutionGateway) {
        this.institutionGateway = Objects.requireNonNull(institutionGateway);
    }

    @Override
    public Set<InstitutionListOutput> execute() {
        return this.institutionGateway.findAll()
                .stream()
                .map(InstitutionListOutput::from)
                .collect(Collectors.toSet());
    }
}
