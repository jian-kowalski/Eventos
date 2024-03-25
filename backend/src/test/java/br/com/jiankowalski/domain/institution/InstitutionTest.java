package br.com.jiankowalski.domain.institution;

import br.com.jiankowalski.domain.exceptions.NotificationException;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

@QuarkusTest
class InstitutionTest {

    @Test
    public void dadosParametrosValidos_quandoChamarNovaInstituicao_deveRetornarAInstituicaoInstanciada() {
        final var expectedName = "Contratação";
        final var expectedInstitutionType = InstitutionType.CONFEDERATION;

        final var actualInstituicao = Institution.newInstitution(expectedName, expectedInstitutionType);

        Assertions.assertNotNull(actualInstituicao);
        Assertions.assertNotNull(actualInstituicao.getId());
        Assertions.assertEquals(expectedName, actualInstituicao.getName());
        Assertions.assertEquals(expectedInstitutionType, actualInstituicao.getType());
        Assertions.assertNotNull(actualInstituicao.getCreatedAt());
    }

    @Test
    public void dadosNomeNulo_quandoChamarNovaInstituicao_deveRetornarErro() {
        final String expectedName = null;
        final var expectedInstitutionType = InstitutionType.CONFEDERATION;

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "O nome da instituição não pode ser vazio";
        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            Institution.newInstitution(expectedName, expectedInstitutionType);
        });

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void dadosNomeVazio_quandoChamarNovaInstituicao_deveRetornarErro() {
        final var expectedName = "";
        final var expectedInstitutionType = InstitutionType.CONFEDERATION;

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "O nome da instituição não pode ser vazio";
        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            Institution.newInstitution(expectedName, expectedInstitutionType);
        });

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void dadosNomeComTamanhoMenorQue5_quandoChamarNovaInstituicao_deveRetornarErro() {
        final var expectedName = "abcd";
        final var expectedInstitutionType = InstitutionType.CONFEDERATION;

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "O nome da instituição deve ter entre 5 e 20 caracteres";
        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            Institution.newInstitution(expectedName, expectedInstitutionType);
        });

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

    @Test
    public void dadosNomeComTamanhoMaiorQue20_quandoChamarNovaInstituicao_deveRetornarErro() {
        final var expectedName = "Centro Universitário de Ciências Humanas e Sociais Aplicadas";
        final var expectedInstitutionType = InstitutionType.CONFEDERATION;

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "O nome da instituição deve ter entre 5 e 20 caracteres";
        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            Institution.newInstitution(expectedName, expectedInstitutionType);
        });

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());
    }

}