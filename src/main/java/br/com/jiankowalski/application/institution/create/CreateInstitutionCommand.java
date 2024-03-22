package br.com.jiankowalski.application.institution.create;

import br.com.jiankowalski.domain.institution.InstitutionType;

public record CreateInstitutionCommand(
        String name,
        InstitutionType type
) {

    public static CreateInstitutionCommand of(String name, InstitutionType type) {
        return new CreateInstitutionCommand(name, type);
    }
    
}
