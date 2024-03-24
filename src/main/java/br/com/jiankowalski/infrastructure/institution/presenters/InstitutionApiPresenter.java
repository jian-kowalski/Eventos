package br.com.jiankowalski.infrastructure.institution.presenters;

import br.com.jiankowalski.application.institution.list.InstitutionListOutput;
import br.com.jiankowalski.infrastructure.institution.models.InstitutionListResponse;

public interface InstitutionApiPresenter {


    static InstitutionListResponse present(final InstitutionListOutput output) {
        return new InstitutionListResponse(
                output.id(),
                output.name(),
                output.type().description(),
                output.createdAt()
        );
    }
}
