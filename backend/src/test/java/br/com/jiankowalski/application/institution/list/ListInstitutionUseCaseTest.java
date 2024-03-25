package br.com.jiankowalski.application.institution.list;

import br.com.jiankowalski.application.UseCaseTest;
import br.com.jiankowalski.domain.institution.Institution;
import br.com.jiankowalski.domain.institution.InstitutionGateway;
import br.com.jiankowalski.domain.institution.InstitutionType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.mockito.Mockito.when;

class ListInstitutionUseCaseTest extends UseCaseTest {

    @InjectMocks
    private DefaultListInstitutionsUseCase useCase;

    @Mock
    private InstitutionGateway institutionGateway;

    @Override
    protected List<Object> getMocks() {
        return List.of(institutionGateway);
    }

    @Test
    public void dataAConsultaValida_quandoChamarListarInstituicoes_deveRetornarAListaDeInstituicoes() {
        final var institutions = Set.of(
                Institution.newInstitution("Escola", InstitutionType.CONFEDERATION),
                Institution.newInstitution("Universidade", InstitutionType.COOPERATIVE)
        );
        final var expectedItemsCount = 2;
        final var expectedResult = institutions.stream().map(InstitutionListOutput::from)
                .collect(Collectors.toSet());

        when(institutionGateway.findAll())
                .thenReturn(institutions);

        final var actualResult = useCase.execute();

        Assertions.assertEquals(expectedItemsCount, actualResult.size());
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void dataAConsultaValida_quandoChamarListarNaoHouverInstituicoes_deveRetornarAListaVazia() {
        final var institutions = Set.<Institution>of();
        final var expectedItemsCount = 0;
        final var expectedResult = institutions.stream().map(InstitutionListOutput::from)
                .collect(Collectors.toSet());

        when(institutionGateway.findAll())
                .thenReturn(institutions);

        final var actualResult = useCase.execute();

        Assertions.assertEquals(expectedItemsCount, actualResult.size());
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void dataAConsultaValida_quandoOcorrerErroDeGateway_deveRetornarError() {

        final var expectedErrorMessage = "Gateway error";

        when(institutionGateway.findAll())
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        final var actualException =
                Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute());

        Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());
    }
}