package br.com.jiankowalski.infrastructure.institution.models;

import java.time.Instant;

public record InstitutionListResponse(
        String id,
        String name,
        String type,
        Instant createdAt
) {
}
