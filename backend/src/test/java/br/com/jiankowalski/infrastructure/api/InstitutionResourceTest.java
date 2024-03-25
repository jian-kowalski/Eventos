package br.com.jiankowalski.infrastructure.api;

import br.com.jiankowalski.application.institution.create.CreateInstitutionOutput;
import br.com.jiankowalski.application.institution.create.CreateInstitutionUseCase;
import br.com.jiankowalski.application.institution.list.InstitutionListOutput;
import br.com.jiankowalski.application.institution.list.ListInstitutionUseCase;
import br.com.jiankowalski.domain.exceptions.NotificationException;
import br.com.jiankowalski.domain.institution.Institution;
import br.com.jiankowalski.domain.institution.InstitutionType;
import br.com.jiankowalski.domain.utils.IdUtils;
import br.com.jiankowalski.domain.validation.handler.Notification;
import br.com.jiankowalski.infrastructure.institution.models.CreateInstitutionRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.Response;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;

@QuarkusTest
class InstitutionResourceTest {

    @InjectMock
    CreateInstitutionUseCase createInstitutionUseCase;

    @InjectMock
    ListInstitutionUseCase listInstitutionUseCase;

    @Inject
    private ObjectMapper mapper;

    @Test
    void dadosParametrosValidos_quandoChamarNovaInstituicao_deveRetornarOId() throws Exception {
        final var expectedId = IdUtils.uuid();
        final var expectedName = "Contratação";
        final var expectedInstitutionType = InstitutionType.CONFEDERATION;

        final var request = new CreateInstitutionRequest(expectedName,
                expectedInstitutionType.name()
        );

        final var output = CreateInstitutionOutput.from(expectedId);

        Mockito.when(createInstitutionUseCase.execute(Mockito.any())).thenReturn(output);

        given()
                .contentType(ContentType.JSON)
                .body(this.mapper.writeValueAsString(request))
                .when()
                .post("/institutions")
                .then()
                .statusCode(Response.Status.CREATED.getStatusCode())
                .header("location", "http://localhost:8081/institutions/".concat(expectedId));
    }

    @Test
    public void dadosNomeNulo_quandoChamarNovaInstituicao_deveRetornarUmaNotificao() throws Exception {
        final String expectedName = null;
        final var expectedInstitutionType = InstitutionType.CONFEDERATION;

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "O nome da instituição não pode ser vazio";

        final var request = new CreateInstitutionRequest(expectedName,
                expectedInstitutionType.name()
        );

        Mockito.when(createInstitutionUseCase.execute(Mockito.any()))
                .thenThrow(new NotificationException("Error", Notification.create(new Error(expectedErrorMessage))));

        given()
                .contentType(ContentType.JSON)
                .body(this.mapper.writeValueAsString(request))
                .when()
                .post("/institutions")
                .then()
                .statusCode(422)
                .header("location", nullValue())
                .and()
                .body("erros", hasSize(expectedErrorCount))
                .body("erros[0].message", equalTo(expectedErrorMessage));
    }

    @Test
    public void dadosNomeVazio_quandoChamarNovaInstituicao_deveRetornarErro() throws Exception {
        final var expectedName = "";
        final var expectedInstitutionType = InstitutionType.CONFEDERATION;

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "O nome da instituição não pode ser vazio";

        final var request = new CreateInstitutionRequest(expectedName,
                expectedInstitutionType.name()
        );

        Mockito.when(createInstitutionUseCase.execute(Mockito.any()))
                .thenThrow(new NotificationException("Error", Notification.create(new Error(expectedErrorMessage))));

        given()
                .contentType(ContentType.JSON)
                .body(this.mapper.writeValueAsString(request))
                .when()
                .post("/institutions")
                .then()
                .statusCode(422)
                .header("location", nullValue())
                .and()
                .body("erros", hasSize(expectedErrorCount))
                .body("erros[0].message", equalTo(expectedErrorMessage));
    }

    @Test
    public void dadosNomeComTamanhoMenorQue5_quandoChamarNovaInstituicao_deveRetornarErro() throws Exception {
        final var expectedName = "abcd";
        final var expectedInstitutionType = InstitutionType.CONFEDERATION;

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "O nome da instituição deve ter entre 5 e 20 caracteres";

        final var request = new CreateInstitutionRequest(expectedName,
                expectedInstitutionType.name()
        );

        Mockito.when(createInstitutionUseCase.execute(Mockito.any()))
                .thenThrow(new NotificationException("Error", Notification.create(new Error(expectedErrorMessage))));

        given()
                .contentType(ContentType.JSON)
                .body(this.mapper.writeValueAsString(request))
                .when()
                .post("/institutions")
                .then()
                .statusCode(422)
                .header("location", nullValue())
                .and()
                .body("erros", hasSize(expectedErrorCount))
                .body("erros[0].message", equalTo(expectedErrorMessage));
    }

    @Test
    public void dadosNomeComTamanhoMaiorQue20_quandoChamarNovaInstituicao_deveRetornarErro() throws Exception {
        final var expectedName = "Centro Universitário de Ciências Humanas e Sociais Aplicadas";
        final var expectedInstitutionType = InstitutionType.CONFEDERATION;

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "O nome da instituição deve ter entre 5 e 20 caracteres";

        final var request = new CreateInstitutionRequest(expectedName,
                expectedInstitutionType.name()
        );

        Mockito.when(createInstitutionUseCase.execute(Mockito.any()))
                .thenThrow(new NotificationException("Error", Notification.create(new Error(expectedErrorMessage))));

        given()
                .contentType(ContentType.JSON)
                .body(this.mapper.writeValueAsString(request))
                .when()
                .post("/institutions")
                .then()
                .statusCode(422)
                .header("location", nullValue())
                .and()
                .body("erros", hasSize(expectedErrorCount))
                .body("erros[0].message", equalTo(expectedErrorMessage));
    }

    @Test
    public void dadosParametrosValidos_quandoChamarListarInstituicoes_deveRetornarAsInstituicoes() throws Exception {
        final var aInstitution = Institution.newInstitution("Escola", InstitutionType.COOPERATIVE);

        final var expectedItems = Set.of(InstitutionListOutput.from(aInstitution));

        when(listInstitutionUseCase.execute())
                .thenReturn(expectedItems);

        given()
                .when()
                .get("/institutions")
                .then()
                .statusCode(200)
                .header("location", nullValue())
                .and()
                .body("[0].id", equalTo(aInstitution.getId().getValue()))
                .body("[0].name", equalTo(aInstitution.getName()))
                .body("[0].type", equalTo(aInstitution.getType().description()))
                .body("[0].createdAt", equalTo(aInstitution.getCreatedAt().toString()));

    }


}