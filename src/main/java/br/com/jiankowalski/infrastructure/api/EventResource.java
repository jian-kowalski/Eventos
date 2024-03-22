package br.com.jiankowalski.infrastructure.api;

import br.com.jiankowalski.application.event.create.CreateEventCommand;
import br.com.jiankowalski.application.event.create.CreateEventUseCase;
import br.com.jiankowalski.infrastructure.event.models.CreateEventRequest;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.Objects;

import static br.com.jiankowalski.domain.utils.InstantUtils.stringToInstant;

@Path("/events")
public class EventResource {

    private final CreateEventUseCase createEventUseCase;

    public EventResource(final CreateEventUseCase createEventUseCase) {
        this.createEventUseCase = Objects.requireNonNull(createEventUseCase);
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public RestResponse<String> createEvent(final CreateEventRequest request, @Context UriInfo uriInfo) {
        final var aStartDate = stringToInstant(request.aStartDate());
        final var aEndDate = stringToInstant(request.aEndDate());
        final var aCommand = CreateEventCommand.of(request.aName(), aStartDate, aEndDate, request.institution());
        final var output = this.createEventUseCase.execute(aCommand);
        return RestResponse.seeOther(uriInfo.getAbsolutePathBuilder().path(output.id()).build());
    }
}
