package br.com.jiankowalski.infrastructure.api;

import br.com.jiankowalski.application.institution.create.CreateInstitutionCommand;
import br.com.jiankowalski.application.institution.create.CreateInstitutionUseCase;
import br.com.jiankowalski.application.institution.list.ListInstitutionUseCase;
import br.com.jiankowalski.domain.exceptions.NotificationException;
import br.com.jiankowalski.domain.institution.InstitutionType;
import br.com.jiankowalski.domain.validation.Error;
import br.com.jiankowalski.infrastructure.institution.models.CreateInstitutionRequest;
import br.com.jiankowalski.infrastructure.institution.models.InstitutionListResponse;
import br.com.jiankowalski.infrastructure.institution.presenters.InstitutionApiPresenter;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Path("institutions")
public class InstitutionResource {

    private final CreateInstitutionUseCase createInstitutionUseCase;
    private final ListInstitutionUseCase listInstutionUseCase;

    public InstitutionResource(final CreateInstitutionUseCase createInstitutionUseCase,
                               final ListInstitutionUseCase listInstutionUseCase) {
        this.createInstitutionUseCase = Objects.requireNonNull(createInstitutionUseCase);
        this.listInstutionUseCase = Objects.requireNonNull(listInstutionUseCase);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResponse<String> createEvent(final CreateInstitutionRequest request, @Context UriInfo uriInfo) {
        final var aType = InstitutionType.of(request.type())
                .orElseThrow(() -> NotificationException.with(new Error("O tipo %s não encontrado".formatted(request.type()))));

        final var aCommand = CreateInstitutionCommand.of(request.name(), aType);
        final var output = this.createInstitutionUseCase.execute(aCommand);
        return RestResponse.created(uriInfo.getAbsolutePathBuilder().path(output.id()).build());
    }

    @GET
    public Set<InstitutionListResponse> listInstitutions() {
        return listInstutionUseCase.execute().stream()
                .map(InstitutionApiPresenter::present)
                .collect(Collectors.toSet());
    }
}
