package br.com.jiankowalski.application.event.create;

public record CreateEventOutput(String id) {
    public static CreateEventOutput from(final String anId) {
        return new CreateEventOutput(anId);
    }
}
