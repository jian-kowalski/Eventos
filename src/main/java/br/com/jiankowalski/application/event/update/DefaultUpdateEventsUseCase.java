package br.com.jiankowalski.application.event.update;

import br.com.jiankowalski.domain.event.Event;
import br.com.jiankowalski.domain.event.EventGateway;

import java.util.Objects;

public class DefaultUpdateEventsUseCase extends UpdateEventsUseCase {

    private static final int PER_PAGE = 10;
    private final EventGateway eventGateway;

    public DefaultUpdateEventsUseCase(final EventGateway eventGateway) {
        this.eventGateway = Objects.requireNonNull(eventGateway);
    }

    @Override
    public Boolean execute() {
        var page = 0;
        var containsEvents = true;
        try {
            do {
                final var events = this.eventGateway.findProcessToday(page, PER_PAGE);
                events.forEach(Event::updateStatus);
                this.eventGateway.update(events);
                page++;
                containsEvents = !events.isEmpty();
            } while (containsEvents);
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
