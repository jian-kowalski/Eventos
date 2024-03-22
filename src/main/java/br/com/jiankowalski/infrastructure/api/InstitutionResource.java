package br.com.jiankowalski.infrastructure.api;

import br.com.jiankowalski.application.institution.create.CreateInstitutionCommand;
import br.com.jiankowalski.application.institution.create.CreateInstitutionUseCase;
import br.com.jiankowalski.domain.exceptions.NotificationException;
import br.com.jiankowalski.domain.institution.InstitutionType;
import br.com.jiankowalski.domain.validation.Error;
import br.com.jiankowalski.infrastructure.institution.models.CreateInstitutionRequest;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;

import java.net.URI;
import java.util.Objects;

@Path("/institutions")
public class InstitutionResource {

    private final CreateInstitutionUseCase createInstitutionUseCase;

    public InstitutionResource(final CreateInstitutionUseCase createInstitutionUseCase) {
        this.createInstitutionUseCase = Objects.requireNonNull(createInstitutionUseCase);
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public Response createEvent(final CreateInstitutionRequest request, @Context UriInfo uriInfo) {
        final var aType = InstitutionType.of(request.type())
                .orElseThrow(() -> NotificationException.with(new Error("O tipo %s não encontrado".formatted(request.type()))));

        final var aCommand = CreateInstitutionCommand.of(request.name(), aType);
        final var output = this.createInstitutionUseCase.execute(aCommand);
        return Response.ok().location(URI.create(uriInfo.getPath().concat(output.id()))).build();
    }
}
