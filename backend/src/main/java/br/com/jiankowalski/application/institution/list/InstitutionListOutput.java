package br.com.jiankowalski.application.institution.list;

import br.com.jiankowalski.domain.institution.Institution;
import br.com.jiankowalski.domain.institution.InstitutionType;

import java.time.Instant;

public record InstitutionListOutput(
        String id,
        String name,
        InstitutionType type,
        Instant createdAt
) {

    public static InstitutionListOutput from(final Institution aInstitution) {
        return new InstitutionListOutput(
                aInstitution.getId().getValue(),
                aInstitution.getName(),
                aInstitution.getType(),
                aInstitution.getCreatedAt()
        );
    }
}
