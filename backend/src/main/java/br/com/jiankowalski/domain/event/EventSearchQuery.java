package br.com.jiankowalski.domain.event;

import br.com.jiankowalski.domain.institution.InstitutionID;

public record EventSearchQuery(
        int page,
        int perPage,
        InstitutionID institution
) {
}
