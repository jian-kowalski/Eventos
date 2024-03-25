package br.com.jiankowalski.infrastructure.institution.models;

public record CreateInstitutionRequest(
        String name,
        String type
) {
}
