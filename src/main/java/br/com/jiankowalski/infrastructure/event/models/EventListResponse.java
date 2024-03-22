package br.com.jiankowalski.infrastructure.event.models;

public record EventListResponse(
        String id,
        String name,
        boolean active,
        String startAt,
        String finishAt,
        String createdAt
) {
}
