package br.com.jiankowalski.application.event.create;

import br.com.jiankowalski.application.UseCaseTest;
import br.com.jiankowalski.domain.event.EventGateway;
import br.com.jiankowalski.domain.exceptions.NotificationException;
import br.com.jiankowalski.domain.institution.InstitutionGateway;
import br.com.jiankowalski.domain.institution.InstitutionID;
import br.com.jiankowalski.domain.utils.IdUtils;
import br.com.jiankowalski.domain.utils.InstantUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

class DefaultCreateEventUseCaseTest extends UseCaseTest {
    @InjectMocks
    private DefaultCreateEventUseCase useCase;

    @Mock
    private EventGateway eventGateway;

    @Mock
    private InstitutionGateway institutionGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(eventGateway, institutionGateway);
    }

    @Test
    public void dadosParametrosValidos_quandoChamarNovoEvento_deveRetornarOEventoInstanciado() {
        final var expectedName = "Contratação";
        final var expectedIsActive = true;
        final var expectedStartDate = InstantUtils.now().minus(1, ChronoUnit.DAYS);
        final var expectedEndDate = InstantUtils.now().plus(1, ChronoUnit.DAYS);
        final var expectedInstitutionID = InstitutionID.from(IdUtils.uuid());

        final var aCommand =
                CreateEventCommand.of(expectedName, expectedStartDate, expectedEndDate, expectedInstitutionID.getValue());

        when(eventGateway.create(any()))
                .thenAnswer(returnsFirstArg());

        when(institutionGateway.existsById(any()))
                .thenReturn(true);

        final var actualOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        verify(eventGateway, times(1)).create(argThat(aEvent ->
                Objects.equals(expectedName, aEvent.getName())
                        && Objects.equals(expectedIsActive, aEvent.isActive())
                        && Objects.equals(expectedStartDate, aEvent.getStartAt())
                        && Objects.equals(expectedEndDate, aEvent.getFinishAt())
                        && Objects.equals(expectedInstitutionID, aEvent.getInstitutionID())
                        && Objects.nonNull(aEvent.getCreatedAt())
                        && Objects.nonNull(aEvent.getUpdatedAt())
                        && Objects.isNull(aEvent.getDeletedAt())
        ));
    }

    @Test
    public void dadosNomeNulo_quandoChamarNovoEvento_deveRetornarErro() {
        final String expectedName = null;
        final var expectedStartDate = InstantUtils.now().minus(1, ChronoUnit.DAYS);
        final var expectedEndDate = InstantUtils.now().plus(1, ChronoUnit.DAYS);
        final var expectedInstitutionID = InstitutionID.from(IdUtils.uuid());
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "O nome do evento não pode ser vazio";

        final var aCommand =
                CreateEventCommand.of(expectedName, expectedStartDate, expectedEndDate, expectedInstitutionID.getValue());

        when(institutionGateway.existsById(any()))
                .thenReturn(true);

        final var actualException = Assertions.assertThrows(NotificationException.class, () ->
                useCase.execute(aCommand)
        );

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        Mockito.verify(eventGateway, times(0)).create(any());
    }

    @Test
    public void dadosNomeVazio_quandoChamarNovoEvento_deveRetornarErro() {
        final var expectedName = "";
        final var expectedStartDate = InstantUtils.now().minus(1, ChronoUnit.DAYS);
        final var expectedEndDate = InstantUtils.now().plus(1, ChronoUnit.DAYS);
        final var expectedInstitutionID = InstitutionID.from(IdUtils.uuid());
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "O nome do evento não pode ser vazio";

        final var aCommand =
                CreateEventCommand.of(expectedName, expectedStartDate, expectedEndDate, expectedInstitutionID.getValue());

        when(institutionGateway.existsById(any()))
                .thenReturn(true);

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            useCase.execute(aCommand);
        });

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        Mockito.verify(eventGateway, times(0)).create(any());
    }

    @Test
    public void dadosNomeComTamanhoMenorQue3_quandoChamarNovoEvento_deveRetornarErro() {
        final var expectedName = "ab";
        final var expectedStartDate = InstantUtils.now().minus(1, ChronoUnit.DAYS);
        final var expectedEndDate = InstantUtils.now().plus(1, ChronoUnit.DAYS);
        final var expectedInstitutionID = InstitutionID.from(IdUtils.uuid());
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "O nome do evento deve ter entre 3 e 60 caracteres";

        final var aCommand =
                CreateEventCommand.of(expectedName, expectedStartDate, expectedEndDate, expectedInstitutionID.getValue());

        when(institutionGateway.existsById(any()))
                .thenReturn(true);

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            useCase.execute(aCommand);
        });

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        Mockito.verify(eventGateway, times(0)).create(any());
    }

    @Test
    public void dadoNomeComTamanhoMaiorQue60_quandoChamarNovoEvento_deveRetornarErro() {
        final var expectedName = "Evite palavras desnecessárias e use apenas as informações essenciais";
        final var expectedStartDate = InstantUtils.now().minus(1, ChronoUnit.DAYS);
        final var expectedEndDate = InstantUtils.now().plus(1, ChronoUnit.DAYS);
        final var expectedInstitutionID = InstitutionID.from(IdUtils.uuid());
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "O nome do evento deve ter entre 3 e 60 caracteres";

        final var aCommand =
                CreateEventCommand.of(expectedName, expectedStartDate, expectedEndDate, expectedInstitutionID.getValue());

        when(institutionGateway.existsById(any()))
                .thenReturn(true);

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            useCase.execute(aCommand);
        });

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        Mockito.verify(eventGateway, times(0)).create(any());
    }

    @Test
    public void dadaDataInicialMaiorQueADataFim_quandoChamarNovoEvento_deveRetornarErro() {
        final var expectedName = "Contratação";
        final var expectedStartDate = InstantUtils.now().plus(1, ChronoUnit.DAYS);
        final var expectedEndDate = InstantUtils.now().minus(1, ChronoUnit.DAYS);
        final var expectedInstitutionID = InstitutionID.from(IdUtils.uuid());
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "A data de termino do evento deve ser maior que a data de inicio";

        final var aCommand =
                CreateEventCommand.of(expectedName, expectedStartDate, expectedEndDate, expectedInstitutionID.getValue());

        when(institutionGateway.existsById(any()))
                .thenReturn(true);

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            useCase.execute(aCommand);
        });

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        Mockito.verify(eventGateway, times(0)).create(any());
    }

    @Test
    public void dadaDataInicialNula_quandoChamarNovoEvento_deveRetornarErro() {
        final var expectedName = "Contratação";
        final var expectedStartDate = InstantUtils.now().plus(1, ChronoUnit.DAYS);
        final Instant expectedEndDate = null;
        final var expectedInstitutionID = InstitutionID.from(IdUtils.uuid());
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "A data de termino do evento não pode ser vazia";

        final var aCommand =
                CreateEventCommand.of(expectedName, expectedStartDate, expectedEndDate, expectedInstitutionID.getValue());

        when(institutionGateway.existsById(any()))
                .thenReturn(true);

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            useCase.execute(aCommand);
        });

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        Mockito.verify(eventGateway, times(0)).create(any());
    }

    @Test
    public void dadaDataFinalNula_quandoChamarNovoEvento_deveRetornarErro() {
        final var expectedName = "Contratação";
        final Instant expectedStartDate = null;
        final var expectedEndDate = InstantUtils.now().minus(1, ChronoUnit.DAYS);
        final var expectedInstitutionID = InstitutionID.from(IdUtils.uuid());
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "A data de inicio do evento não pode ser vazia";

        final var aCommand =
                CreateEventCommand.of(expectedName, expectedStartDate, expectedEndDate, expectedInstitutionID.getValue());

        when(institutionGateway.existsById(any()))
                .thenReturn(true);

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            useCase.execute(aCommand);
        });

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        Mockito.verify(eventGateway, times(0)).create(any());
    }

    @Test
    public void dadaDataInicialEFinalNula_quandoChamarNovoEvento_deveRetornarErro() {
        final var expectedName = "Contratação";
        final Instant expectedStartDate = null;
        final Instant expectedEndDate = null;
        final var expectedInstitutionID = InstitutionID.from(IdUtils.uuid());
        final var expectedErrorCount = 2;
        final var expectedErrorMessageStartDate = "A data de inicio do evento não pode ser vazia";
        final var expectedErrorMessageFinishDate = "A data de inicio do evento não pode ser vazia";

        final var aCommand =
                CreateEventCommand.of(expectedName, expectedStartDate, expectedEndDate, expectedInstitutionID.getValue());

        when(institutionGateway.existsById(any()))
                .thenReturn(true);

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            useCase.execute(aCommand);
        });

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertTrue(actualException.getErrors().stream().anyMatch(error -> error.message().equals(expectedErrorMessageStartDate)));
        Assertions.assertTrue(actualException.getErrors().stream().anyMatch(error -> error.message().equals(expectedErrorMessageFinishDate)));

        Mockito.verify(eventGateway, times(0)).create(any());
    }

    @Test
    public void dadoInstituicaoInvalida_quandoChamarNovoEvento_deveRetornarErro() {
        final var expectedName = "Contratação";

        final var expectedStartDate = InstantUtils.now().minus(1, ChronoUnit.DAYS);
        final var expectedEndDate = InstantUtils.now().plus(1, ChronoUnit.DAYS);
        final var expectedInstitutionID = InstitutionID.from(IdUtils.uuid());
        final var expectedErrorCount = 1;

        final var expectedErrorMessage = "Não foi encontrada a instituição com o id: " + expectedInstitutionID;

        final var aCommand =
                CreateEventCommand.of(expectedName, expectedStartDate, expectedEndDate, expectedInstitutionID.getValue());

        when(institutionGateway.existsById(any()))
                .thenReturn(false);

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            useCase.execute(aCommand);
        });

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        Mockito.verify(eventGateway, times(0)).create(any());
    }
}