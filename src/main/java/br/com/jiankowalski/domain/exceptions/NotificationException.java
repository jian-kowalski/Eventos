package br.com.jiankowalski.domain.exceptions;


import br.com.jiankowalski.domain.validation.handler.Notification;

public class NotificationException extends DomainException {

    public NotificationException(final String aMessage, final Notification notification) {
        super(aMessage, notification.getErrors());
    }
}
