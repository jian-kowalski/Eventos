package br.com.jiankowalski.infrastructure.event.models;

import java.time.Instant;

public record EventListResponse(
        String id,
        String name,
        boolean active,
        Instant startAt,
        Instant finishAt,
        Instant createdAt
) {
}
