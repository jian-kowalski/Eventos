package br.com.jiankowalski.infrastructure.event.models;

public record CreateEventRequest(
        String aName,
        String aStartDate,
        String aEndDate,
        String institution
) {
}
