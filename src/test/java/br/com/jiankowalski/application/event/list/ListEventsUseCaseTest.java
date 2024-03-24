package br.com.jiankowalski.application.event.list;

import br.com.jiankowalski.application.UseCaseTest;
import br.com.jiankowalski.domain.event.Event;
import br.com.jiankowalski.domain.event.EventGateway;
import br.com.jiankowalski.domain.event.EventSearchQuery;
import br.com.jiankowalski.domain.institution.InstitutionID;
import br.com.jiankowalski.domain.pagination.Pagination;
import br.com.jiankowalski.domain.utils.IdUtils;
import br.com.jiankowalski.domain.utils.InstantUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class ListEventsUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultListEventsUseCase useCase;

    @Mock
    private EventGateway eventGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(eventGateway);
    }

    @Test
    public void dataAConsultaValida_quandoChamarListarEventos_deveRetornarAListaDeEventos() {
        final var events = List.of(
                Event.newEvent("Festa da Firma", InstitutionID.from(IdUtils.uuid()), InstantUtils.now(), InstantUtils.now().plus(1, ChronoUnit.DAYS)),
                Event.newEvent("Happy hour", InstitutionID.from(IdUtils.uuid()), InstantUtils.now(), InstantUtils.now().plus(1, ChronoUnit.DAYS))
        );

        final var expectedInstitution = InstitutionID.unique();
        final var expectedPage = 0;
        final var expectedPerPage = 10;


        final var aQuery =
                new EventSearchQuery(expectedPage, expectedPerPage, expectedInstitution);

        final var expectedPagination =
                new Pagination<>(expectedPage, expectedPerPage, events.size(), events);

        final var expectedItemsCount = 2;
        final var expectedResult = expectedPagination.map(EventListOutput::from);

        when(eventGateway.findAll(eq(aQuery)))
                .thenReturn(expectedPagination);

        final var actualResult = useCase.execute(aQuery);

        Assertions.assertEquals(expectedItemsCount, actualResult.items().size());
        Assertions.assertEquals(expectedResult, actualResult);
        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.totalElements());
        Assertions.assertEquals(events.size(), actualResult.totalPages());
    }

    @Test
    public void dataAConsultaValida_quandoChamarListarEventosSemEventos_deveRetornarAListaVazia() {
        final var events = List.<Event>of();

        final var expectedInstitution = InstitutionID.unique();
        final var expectedPage = 0;
        final var expectedPerPage = 10;


        final var aQuery =
                new EventSearchQuery(expectedPage, expectedPerPage, expectedInstitution);

        final var expectedPagination =
                new Pagination<>(expectedPage, expectedPerPage, events.size(), events);

        final var expectedItemsCount = 0;
        final var expectedResult = expectedPagination.map(EventListOutput::from);

        when(eventGateway.findAll(eq(aQuery)))
                .thenReturn(expectedPagination);

        final var actualResult = useCase.execute(aQuery);

        Assertions.assertEquals(expectedItemsCount, actualResult.items().size());
        Assertions.assertEquals(expectedResult, actualResult);
        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.totalElements());
        Assertions.assertEquals(events.size(), actualResult.totalPages());
    }

    @Test
    public void dataAConsultaValida_quandoOcorrerErroDeGateway_deveRetornarError() {

        final var expectedErrorMessage = "Gateway error";

        final var expectedInstitution = InstitutionID.unique();
        final var expectedPage = 0;
        final var expectedPerPage = 10;


        final var aQuery =
                new EventSearchQuery(expectedPage, expectedPerPage, expectedInstitution);

        when(eventGateway.findAll(eq(aQuery)))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        final var actualException =
                Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(aQuery));

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }
}
