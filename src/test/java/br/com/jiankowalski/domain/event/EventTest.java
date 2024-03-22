package br.com.jiankowalski.domain.event;

import br.com.jiankowalski.domain.exceptions.NotificationException;
import br.com.jiankowalski.domain.institution.InstitutionID;
import br.com.jiankowalski.domain.utils.IdUtils;
import br.com.jiankowalski.domain.utils.InstantUtils;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@QuarkusTest
class EventTest {

    @Test
    public void dadosParametrosValidos_quandoChamarNovoEvento_deveRetornarOEventoInstanciado() {
        final var expectedName = "Contratação";
        final var expectedIsActive = false;
        final var expectedStartDate = InstantUtils.now().minus(1, ChronoUnit.DAYS);
        final var expectedEndDate = InstantUtils.now().plus(1, ChronoUnit.DAYS);
        final var expectedInstitutionID = InstitutionID.from(IdUtils.uuid());

        final var actualEvent = Event.newEvent(expectedName, expectedInstitutionID, expectedStartDate, expectedEndDate);

        Assertions.assertNotNull(actualEvent);
        Assertions.assertNotNull(actualEvent.getId());
        Assertions.assertEquals(expectedName, actualEvent.getName());
        Assertions.assertEquals(expectedIsActive, actualEvent.isActive());
        Assertions.assertEquals(expectedInstitutionID, actualEvent.getInstitutionID());
        Assertions.assertEquals(expectedStartDate, actualEvent.getStartAt());
        Assertions.assertEquals(expectedEndDate, actualEvent.getFinishAt());
        Assertions.assertNotNull(actualEvent.getCreatedAt());
        Assertions.assertNotNull(actualEvent.getUpdatedAt());
        Assertions.assertNull(actualEvent.getDeletedAt());
    }

    @Test
    public void dadosNomeNulo_quandoChamarNovoEvento_deveRetornarErro() {
        final String expectedName = null;
        final var expectedStartDate = InstantUtils.now().minus(1, ChronoUnit.DAYS);
        final var expectedEndDate = InstantUtils.now().plus(1, ChronoUnit.DAYS);
        final var expectedInstitutionID = InstitutionID.from(IdUtils.uuid());
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "O nome do evento não pode ser vazio";
        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            Event.newEvent(expectedName, expectedInstitutionID, expectedStartDate, expectedEndDate);
        });

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void dadosNomeVazio_quandoChamarNovoEvento_deveRetornarErro() {
        final var expectedName = "";
        final var expectedStartDate = InstantUtils.now().minus(1, ChronoUnit.DAYS);
        final var expectedEndDate = InstantUtils.now().plus(1, ChronoUnit.DAYS);
        final var expectedInstitutionID = InstitutionID.from(IdUtils.uuid());
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "O nome do evento não pode ser vazio";
        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            Event.newEvent(expectedName, expectedInstitutionID, expectedStartDate, expectedEndDate);
        });

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void dadosNomeComTamanhoMenorQue3_quandoChamarNovoEvento_deveRetornarErro() {
        final var expectedName = "ab";
        final var expectedStartDate = InstantUtils.now().minus(1, ChronoUnit.DAYS);
        final var expectedEndDate = InstantUtils.now().plus(1, ChronoUnit.DAYS);
        final var expectedInstitutionID = InstitutionID.from(IdUtils.uuid());
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "O nome do evento deve ter entre 3 e 60 caracteres";
        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            Event.newEvent(expectedName, expectedInstitutionID, expectedStartDate, expectedEndDate);
        });
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void dadoNomeComTamanhoMaiorQue60_quandoChamarNovoEvento_deveRetornarErro() {
        final var expectedName = "Evite palavras desnecessárias e use apenas as informações essenciais";
        final var expectedStartDate = InstantUtils.now().minus(1, ChronoUnit.DAYS);
        final var expectedEndDate = InstantUtils.now().plus(1, ChronoUnit.DAYS);
        final var expectedInstitutionID = InstitutionID.from(IdUtils.uuid());
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "O nome do evento deve ter entre 3 e 60 caracteres";
        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            Event.newEvent(expectedName, expectedInstitutionID, expectedStartDate, expectedEndDate);
        });
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void dadaDataInicialMaiorQueADataFim_quandoChamarNovoEvento_deveRetornarErro() {
        final var expectedName = "Contratação";
        final var expectedStartDate = InstantUtils.now().plus(1, ChronoUnit.DAYS);
        final var expectedEndDate = InstantUtils.now().minus(1, ChronoUnit.DAYS);
        final var expectedInstitutionID = InstitutionID.from(IdUtils.uuid());
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "A data de termino do evento deve ser maior que a data de inicio";
        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            Event.newEvent(expectedName, expectedInstitutionID, expectedStartDate, expectedEndDate);
        });
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void dadaDataInicialNula_quandoChamarNovoEvento_deveRetornarErro() {
        final var expectedName = "Contratação";
        final var expectedStartDate = InstantUtils.now().plus(1, ChronoUnit.DAYS);
        final Instant expectedEndDate = null;
        final var expectedInstitutionID = InstitutionID.from(IdUtils.uuid());
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "A data de termino do evento não pode ser vazia";
        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            Event.newEvent(expectedName, expectedInstitutionID, expectedStartDate, expectedEndDate);
        });
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void dadaDataFinalNula_quandoChamarNovoEvento_deveRetornarErro() {
        final var expectedName = "Contratação";
        final Instant expectedStartDate = null;
        final var expectedEndDate = InstantUtils.now().minus(1, ChronoUnit.DAYS);
        final var expectedInstitutionID = InstitutionID.from(IdUtils.uuid());
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "A data de inicio do evento não pode ser vazia";
        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            Event.newEvent(expectedName, expectedInstitutionID, expectedStartDate, expectedEndDate);
        });
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
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
        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            Event.newEvent(expectedName, expectedInstitutionID, expectedStartDate, expectedEndDate);
        });
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertTrue(actualException.getErrors().stream().anyMatch(error -> error.message().equals(expectedErrorMessageStartDate)));
        Assertions.assertTrue(actualException.getErrors().stream().anyMatch(error -> error.message().equals(expectedErrorMessageFinishDate)));
    }

}