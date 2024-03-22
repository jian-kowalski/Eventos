package br.com.jiankowalski.application.event.create;

import java.time.Instant;

public record CreateEventCommand(
        String aName,
        String institutionID,
        Instant startAt,
        Instant finishAt
) {
    public static CreateEventCommand of(final String aName,
                                        final Instant aStartDate,
                                        final Instant aEndDate,
                                        final String institution) {
        return new CreateEventCommand(aName, institution, aStartDate, aEndDate);
    }
}
