package br.com.jiankowalski.application.institution.create;

import br.com.jiankowalski.application.UseCaseTest;
import br.com.jiankowalski.domain.exceptions.NotificationException;
import br.com.jiankowalski.domain.institution.InstitutionGateway;
import br.com.jiankowalski.domain.institution.InstitutionType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;
import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

class CreateInstitutionUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultCreateInstitutionUseCase useCase;

    @Mock
    private InstitutionGateway institutionGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(institutionGateway);
    }


    @Test
    public void givenAValidCommand_whenCallsCreateGenre_shouldReturnGenreId() {
        final var expectName = "Instituição Central";
        final var expectedType = InstitutionType.CENTRAL;


        final var aCommand =
                CreateInstitutionCommand.of(expectName, expectedType);

        when(institutionGateway.create(any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(aCommand);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        verify(institutionGateway, times(1)).create(argThat(aInstitution ->
                Objects.equals(expectName, aInstitution.getName())
                        && Objects.equals(expectedType, aInstitution.getType())
                        && Objects.nonNull(aInstitution.getId())
                        && Objects.nonNull(aInstitution.getCreatedAt())
        ));
    }

    @Test
    public void dadosNomeNulo_quandoChamarNovaInstituicao_deveRetornarErro() {
        final String expectedName = null;
        final var expectedInstitutionType = InstitutionType.CONFEDERATION;

        final var expectedErrorMessage = "O nome da instituição não pode ser vazio";
        final var expectedErrorCount = 1;

        final var aCommand =
                CreateInstitutionCommand.of(expectedName, expectedInstitutionType);

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            useCase.execute(aCommand);
        });

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        Mockito.verify(institutionGateway, times(0)).create(any());
    }

    @Test
    public void dadosNomeVazio_quandoChamarNovaInstituicao_deveRetornarErro() {
        final var expectedName = "";
        final var expectedInstitutionType = InstitutionType.CONFEDERATION;

        final var expectedErrorMessage = "O nome da instituição não pode ser vazio";
        final var expectedErrorCount = 1;

        final var aCommand =
                CreateInstitutionCommand.of(expectedName, expectedInstitutionType);

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            useCase.execute(aCommand);
        });

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        Mockito.verify(institutionGateway, times(0)).create(any());
    }

    @Test
    public void dadosNomeComTamanhoMenorQue5_quandoChamarNovaInstituicao_deveRetornarErro() {
        final var expectedName = "abcd";
        final var expectedInstitutionType = InstitutionType.CONFEDERATION;

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "O nome da instituição deve ter entre 5 e 20 caracteres";

        final var aCommand =
                CreateInstitutionCommand.of(expectedName, expectedInstitutionType);

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            useCase.execute(aCommand);
        });

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        Mockito.verify(institutionGateway, times(0)).create(any());
    }

    @Test
    public void dadosNomeComTamanhoMaiorQue20_quandoChamarNovaInstituicao_deveRetornarErro() {
        final var expectedName = "Centro Universitário de Ciências Humanas e Sociais Aplicadas";
        final var expectedInstitutionType = InstitutionType.CONFEDERATION;

        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "O nome da instituição deve ter entre 5 e 20 caracteres";

        final var aCommand =
                CreateInstitutionCommand.of(expectedName, expectedInstitutionType);

        final var actualException = Assertions.assertThrows(NotificationException.class, () -> {
            useCase.execute(aCommand);
        });

        Assertions.assertNotNull(actualException);
        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().get(0).message());

        Mockito.verify(institutionGateway, times(0)).create(any());
    }


}