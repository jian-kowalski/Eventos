package br.com.jiankowalski.infrastructure.api;

import br.com.jiankowalski.application.event.create.CreateEventCommand;
import br.com.jiankowalski.application.event.create.CreateEventUseCase;
import br.com.jiankowalski.infrastructure.event.models.CreateEventRequest;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.Objects;

import static br.com.jiankowalski.domain.utils.InstantUtils.asInstant;

@Path("/events")
public class EventResource {

    private final CreateEventUseCase createEventUseCase;

    public EventResource(final CreateEventUseCase createEventUseCase) {
        this.createEventUseCase = Objects.requireNonNull(createEventUseCase);
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public RestResponse<String> createEvent(final CreateEventRequest request, @Context UriInfo uriInfo) {
        final var aStartDate = asInstant(request.startDate());
        final var aEndDate = asInstant(request.endDate());
        final var aCommand = CreateEventCommand.of(request.name(), aStartDate, aEndDate, request.institution());
        final var output = this.createEventUseCase.execute(aCommand);
        return RestResponse.created(uriInfo.getAbsolutePathBuilder().path(output.id()).build());
    }
}
