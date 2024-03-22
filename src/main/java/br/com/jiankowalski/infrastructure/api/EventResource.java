package br.com.jiankowalski.infrastructure.api;

import br.com.jiankowalski.application.event.create.CreateEventCommand;
import br.com.jiankowalski.application.event.create.CreateEventUseCase;
import br.com.jiankowalski.application.event.list.ListEventsUseCase;
import br.com.jiankowalski.domain.pagination.Pagination;
import br.com.jiankowalski.domain.pagination.SearchQuery;
import br.com.jiankowalski.infrastructure.event.models.CreateEventRequest;
import br.com.jiankowalski.infrastructure.event.models.EventListResponse;
import br.com.jiankowalski.infrastructure.event.presenters.EventApiPresenter;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.UriInfo;
import org.jboss.resteasy.reactive.RestResponse;

import java.util.Objects;

import static br.com.jiankowalski.domain.utils.InstantUtils.asInstant;

@Path("/events")
public class EventResource {

    private final CreateEventUseCase createEventUseCase;
    private final ListEventsUseCase listEventsUseCase;

    public EventResource(final CreateEventUseCase createEventUseCase,
                         final ListEventsUseCase listEventsUseCase) {
        this.createEventUseCase = Objects.requireNonNull(createEventUseCase);
        this.listEventsUseCase = Objects.requireNonNull(listEventsUseCase);
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

    @GET
    public Pagination<EventListResponse> listEvents(@QueryParam("page") @DefaultValue("0") final int page,
                                                    @QueryParam("perPage") @DefaultValue("10") final int perPage) {
        final var aQuery = new SearchQuery(page, perPage);
        return listEventsUseCase.execute(aQuery).map(EventApiPresenter::present);
    }
}
