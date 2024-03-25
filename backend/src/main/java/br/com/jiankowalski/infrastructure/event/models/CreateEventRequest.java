package br.com.jiankowalski.infrastructure.event.models;

public record CreateEventRequest(
        String name,
        String startDate,
        String endDate,
        String institution
) {
}
