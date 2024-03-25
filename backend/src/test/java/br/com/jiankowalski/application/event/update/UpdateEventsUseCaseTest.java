package br.com.jiankowalski.application.event.update;

import br.com.jiankowalski.application.UseCaseTest;
import br.com.jiankowalski.domain.event.Event;
import br.com.jiankowalski.domain.event.EventGateway;
import br.com.jiankowalski.domain.institution.InstitutionID;
import br.com.jiankowalski.domain.utils.IdUtils;
import br.com.jiankowalski.domain.utils.InstantUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Set;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class UpdateEventsUseCaseTest extends UseCaseTest {
    @InjectMocks
    private DefaultUpdateEventsUseCase useCase;

    @Mock
    private EventGateway eventGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(eventGateway);
    }

    @Test
    public void dataAConsultaValida_quandoChamarAtualizarStatusEventos_deveRetornarOk() {
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var events = Set.of(
                Event.newEvent("Festa da Firma", InstitutionID.from(IdUtils.uuid()), InstantUtils.now(), InstantUtils.now().plus(1, ChronoUnit.DAYS)),
                Event.newEvent("Happy hour", InstitutionID.from(IdUtils.uuid()), InstantUtils.now(), InstantUtils.now().plus(1, ChronoUnit.DAYS))
        );

        when(eventGateway.findProcessToday(expectedPage, expectedPerPage))
                .thenReturn(events);

        final var actualOutput = useCase.execute();

        Assertions.assertTrue(actualOutput);

        Mockito.verify(eventGateway, times(1)).findProcessToday(expectedPage, expectedPerPage);

        Mockito.verify(eventGateway, times(1)).update(eq(events));
    }

}