package br.com.jiankowalski.application.institution.create;

public record CreateInstitutionOutput(String id) {
    public static CreateInstitutionOutput from(final String aId) {
        return new CreateInstitutionOutput(aId);
    }
}
