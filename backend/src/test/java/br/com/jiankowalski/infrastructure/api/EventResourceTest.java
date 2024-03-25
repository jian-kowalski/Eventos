package br.com.jiankowalski.infrastructure.api;

import br.com.jiankowalski.application.event.create.CreateEventOutput;
import br.com.jiankowalski.application.event.create.CreateEventUseCase;
import br.com.jiankowalski.application.event.list.EventListOutput;
import br.com.jiankowalski.application.event.list.ListEventsUseCase;
import br.com.jiankowalski.domain.event.Event;
import br.com.jiankowalski.domain.exceptions.NotificationException;
import br.com.jiankowalski.domain.institution.InstitutionID;
import br.com.jiankowalski.domain.pagination.Pagination;
import br.com.jiankowalski.domain.utils.IdUtils;
import br.com.jiankowalski.domain.utils.InstantUtils;
import br.com.jiankowalski.domain.validation.handler.Notification;
import br.com.jiankowalski.infrastructure.event.models.CreateEventRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Objects;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@QuarkusTest
class EventResourceTest {

    @InjectMock
    CreateEventUseCase createEventUseCase;

    @InjectMock
    ListEventsUseCase listEventsUseCase;

    @Inject
    private ObjectMapper mapper;

    @Test
    void dadosParametrosValidos_quandoChamarNovoEvento_deveRetornarOEventoId() throws Exception {
        final var expectedId = IdUtils.uuid();
        final var expectedName = "Contratação";
        final var expectedInstitution = IdUtils.uuid();
        final var expectedStartDate = InstantUtils.now().minus(1, ChronoUnit.DAYS);
        final var expectedEndDate = InstantUtils.now().plus(1, ChronoUnit.DAYS);
        final var request = new CreateEventRequest(expectedName,
                InstantUtils.asString(expectedStartDate),
                InstantUtils.asString(expectedEndDate),
                expectedInstitution
        );

        final var output = CreateEventOutput.from(expectedId);

        Mockito.when(createEventUseCase.execute(Mockito.any())).thenReturn(output);

        given()
                .contentType(ContentType.JSON)
                .body(this.mapper.writeValueAsString(request))
                .when()
                .post("/events")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .header("location", "http://localhost:8081/events/".concat(expectedId));
    }

    @Test
    void dadosNomeNulo_quandoChamarNovoEvento_deveRetornarUmaNotificao() throws Exception {
        final String expectedName = null;
        final var expectedStartDate = InstantUtils.now().minus(1, ChronoUnit.DAYS);
        final var expectedEndDate = InstantUtils.now().plus(1, ChronoUnit.DAYS);
        final var expectedInstitution = IdUtils.uuid();
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "O nome do evento não pode ser vazio";

        final var request = new CreateEventRequest(expectedName,
                InstantUtils.asString(expectedStartDate),
                InstantUtils.asString(expectedEndDate),
                expectedInstitution
        );

        Mockito.when(createEventUseCase.execute(Mockito.any()))
                .thenThrow(new NotificationException("Error", Notification.create(new Error(expectedErrorMessage))));

        given()
                .contentType(ContentType.JSON)
                .body(this.mapper.writeValueAsString(request))
                .when()
                .post("/events")
                .then()
                .statusCode(422)
                .header("location", nullValue())
                .and()
                .body("erros", hasSize(expectedErrorCount))
                .body("erros[0].message", equalTo(expectedErrorMessage));
    }

    @Test
    void dadosNomeVazio_quandoChamarNovoEvento_deveRetornarUmaNotificao() throws Exception {
        final var expectedName = "";
        final var expectedStartDate = InstantUtils.now().minus(1, ChronoUnit.DAYS);
        final var expectedEndDate = InstantUtils.now().plus(1, ChronoUnit.DAYS);
        final var expectedInstitution = IdUtils.uuid();
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "O nome do evento não pode ser vazio";

        final var request = new CreateEventRequest(expectedName,
                InstantUtils.asString(expectedStartDate),
                InstantUtils.asString(expectedEndDate),
                expectedInstitution
        );

        Mockito.when(createEventUseCase.execute(Mockito.any()))
                .thenThrow(new NotificationException("Error", Notification.create(new Error(expectedErrorMessage))));

        given()
                .contentType(ContentType.JSON)
                .body(this.mapper.writeValueAsString(request))
                .when()
                .post("/events")
                .then()
                .statusCode(422)
                .header("location", nullValue())
                .and()
                .body("erros", hasSize(expectedErrorCount))
                .body("erros[0].message", equalTo(expectedErrorMessage));
    }

    @Test
    public void dadosNomeComTamanhoMenorQue3_quandoChamarNovoEvento_deveRetornarUmaNotificao() throws Exception {
        final var expectedName = "ab";
        final var expectedStartDate = InstantUtils.now().minus(1, ChronoUnit.DAYS);
        final var expectedEndDate = InstantUtils.now().plus(1, ChronoUnit.DAYS);
        final var expectedInstitution = IdUtils.uuid();
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "O nome do evento deve ter entre 3 e 60 caracteres";

        final var request = new CreateEventRequest(expectedName,
                InstantUtils.asString(expectedStartDate),
                InstantUtils.asString(expectedEndDate),
                expectedInstitution
        );

        Mockito.when(createEventUseCase.execute(Mockito.any()))
                .thenThrow(new NotificationException("Error", Notification.create(new Error(expectedErrorMessage))));

        given()
                .contentType(ContentType.JSON)
                .body(this.mapper.writeValueAsString(request))
                .when()
                .post("/events")
                .then()
                .statusCode(422)
                .header("location", nullValue())
                .and()
                .body("erros", hasSize(expectedErrorCount))
                .body("erros[0].message", equalTo(expectedErrorMessage));
    }

    @Test
    public void dadoNomeComTamanhoMaiorQue60_quandoChamarNovoEvento_deveRetornarUmaNotificao() throws Exception {
        final var expectedName = "Evite palavras desnecessárias e use apenas as informações essenciais";
        final var expectedStartDate = InstantUtils.now().minus(1, ChronoUnit.DAYS);
        final var expectedEndDate = InstantUtils.now().plus(1, ChronoUnit.DAYS);
        final var expectedInstitution = IdUtils.uuid();
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "O nome do evento deve ter entre 3 e 60 caracteres";

        final var request = new CreateEventRequest(expectedName,
                InstantUtils.asString(expectedStartDate),
                InstantUtils.asString(expectedEndDate),
                expectedInstitution
        );

        Mockito.when(createEventUseCase.execute(Mockito.any()))
                .thenThrow(new NotificationException("Error", Notification.create(new Error(expectedErrorMessage))));

        given()
                .contentType(ContentType.JSON)
                .body(this.mapper.writeValueAsString(request))
                .when()
                .post("/events")
                .then()
                .statusCode(422)
                .header("location", nullValue())
                .and()
                .body("erros", hasSize(expectedErrorCount))
                .body("erros[0].message", equalTo(expectedErrorMessage));
    }

    @Test
    public void dadaDataInicialMaiorQueADataFim_quandoChamarNovoEvento_deveRetornarUmaNotificao() throws Exception {
        final var expectedName = "Contratação";
        final var expectedStartDate = InstantUtils.now().plus(1, ChronoUnit.DAYS);
        final var expectedEndDate = InstantUtils.now().minus(1, ChronoUnit.DAYS);
        final var expectedInstitution = IdUtils.uuid();
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "A data de termino do evento deve ser maior que a data de inicio";
        final var request = new CreateEventRequest(expectedName,
                InstantUtils.asString(expectedStartDate),
                InstantUtils.asString(expectedEndDate),
                expectedInstitution
        );

        Mockito.when(createEventUseCase.execute(Mockito.any()))
                .thenThrow(new NotificationException("Error", Notification.create(new Error(expectedErrorMessage))));

        given()
                .contentType(ContentType.JSON)
                .body(this.mapper.writeValueAsString(request))
                .when()
                .post("/events")
                .then()
                .statusCode(422)
                .header("location", nullValue())
                .and()
                .body("erros", hasSize(expectedErrorCount))
                .body("erros[0].message", equalTo(expectedErrorMessage));
    }

    @Test
    public void dadaDataInicialNula_quandoChamarNovoEvento_deveRetornarUmaNotificao() throws Exception {
        final var expectedName = "Contratação";
        final var expectedStartDate = InstantUtils.now().plus(1, ChronoUnit.DAYS);
        final Instant expectedEndDate = null;
        final var expectedInstitution = IdUtils.uuid();
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "A data de termino do evento não pode ser vazia";
        final var request = new CreateEventRequest(expectedName,
                InstantUtils.asString(expectedStartDate),
                InstantUtils.asString(expectedEndDate),
                expectedInstitution
        );

        Mockito.when(createEventUseCase.execute(Mockito.any()))
                .thenThrow(new NotificationException("Error", Notification.create(new Error(expectedErrorMessage))));

        given()
                .contentType(ContentType.JSON)
                .body(this.mapper.writeValueAsString(request))
                .when()
                .post("/events")
                .then()
                .statusCode(422)
                .header("location", nullValue())
                .and()
                .body("erros", hasSize(expectedErrorCount))
                .body("erros[0].message", equalTo(expectedErrorMessage));
    }

    @Test
    public void dadaDataFinalNula_quandoChamarNovoEvento_deveRetornarUmaNotificao() throws Exception {
        final var expectedName = "Contratação";
        final Instant expectedStartDate = InstantUtils.now().plus(1, ChronoUnit.DAYS);
        final Instant expectedEndDate = InstantUtils.now().plus(1, ChronoUnit.DAYS);
        final var expectedInstitution = IdUtils.uuid();
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "A data de inicio do evento não pode ser vazia";
        final var request = new CreateEventRequest(expectedName,
                InstantUtils.asString(expectedStartDate),
                InstantUtils.asString(expectedEndDate),
                expectedInstitution
        );

        Mockito.when(createEventUseCase.execute(Mockito.any()))
                .thenThrow(new NotificationException("Error", Notification.create(new Error(expectedErrorMessage))));

        given()
                .contentType(ContentType.JSON)
                .body(this.mapper.writeValueAsString(request))
                .when()
                .post("/events")
                .then()
                .statusCode(422)
                .header("location", nullValue())
                .and()
                .body("erros", hasSize(expectedErrorCount))
                .body("erros[0].message", equalTo(expectedErrorMessage));
    }

    @Test
    public void dadosParametrosValidos_quandoChamarListarEventos_deveRetornarOsEventos() throws Exception {
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedInstitution = InstitutionID.from(IdUtils.uuid());
        final var expectedTotal = 1;

        final var aEvent = Event.newEvent("Contratação", expectedInstitution, InstantUtils.now(), InstantUtils.now());

        final var expectedItems = List.of(EventListOutput.from(aEvent));

        when(listEventsUseCase.execute(Mockito.any()))
                .thenReturn(new Pagination<>(expectedPage, expectedPerPage, expectedTotal, expectedItems));

        given()
                .queryParam("page", expectedPage)
                .queryParam("perPage", expectedPerPage)
                .queryParam("institution", expectedInstitution.getValue())
                .when()
                .get("/events")
                .then()
                .statusCode(200)
                .header("location", nullValue())
                .and()
                .body("currentPage", equalTo(expectedPage))
                .body("totalElements", equalTo(expectedPerPage))
                .body("totalPages", equalTo(expectedTotal))
                .body("items[0].id", equalTo(aEvent.getId().getValue()))
                .body("items[0].name", equalTo(aEvent.getName()))
                .body("items[0].active", equalTo(aEvent.isActive()))
                .body("items[0].startAt", equalTo(aEvent.getStartAt().toString()))
                .body("items[0].finishAt", equalTo(aEvent.getFinishAt().toString()))
                .body("items[0].createdAt", equalTo(aEvent.getCreatedAt().toString()));

        verify(listEventsUseCase, times(1)).execute(argThat(query ->
                Objects.equals(expectedPage, query.page())
                        && Objects.equals(expectedPerPage, query.perPage())
                        && Objects.equals(expectedInstitution, query.institution())
        ));
    }
}