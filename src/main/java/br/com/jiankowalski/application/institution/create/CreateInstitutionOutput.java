package br.com.jiankowalski.application.institution.create;

import br.com.jiankowalski.domain.institution.Institution;

public record CreateInstitutionOutput(String id) {
    public static CreateInstitutionOutput from(final Institution institution) {
        return new CreateInstitutionOutput(institution.getId().getValue());
    }
}
