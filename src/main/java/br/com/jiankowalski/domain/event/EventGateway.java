package br.com.jiankowalski.domain.event;

public interface EventGateway {

    Event create(Event aEvent);

    Event update(Event aEvent);
}
