package br.com.jiankowalski.domain.event;

import br.com.jiankowalski.domain.utils.InstantUtils;
import br.com.jiankowalski.domain.validation.Error;
import br.com.jiankowalski.domain.validation.ValidationHandler;
import br.com.jiankowalski.domain.validation.Validator;

class EventValidator extends Validator {

    public static final int NAME_MAX_LENGTH = 60;
    public static final int NAME_MIN_LENGTH = 3;

    private final Event event;

    protected EventValidator(final Event aEvent, final ValidationHandler aHandler) {
        super(aHandler);
        this.event = aEvent;
    }

    @Override
    public void validate() {
        checkNameConstraints();

        checkDatesConstraints();
    }

    private void checkDatesConstraints() {
        final var startAt = this.event.getStartAt();
        final var finishAt = this.event.getFinishAt();
        if (startAt == null) {
            this.validationHandler().append(new Error("A data de inicio do evento não pode ser vazia"));
        }

        if (finishAt == null) {
            this.validationHandler().append(new Error("A data de termino do evento não pode ser vazia"));
        }

        if (InstantUtils.isAfter(startAt, finishAt)) {
            this.validationHandler().append(new Error("A data de termino do evento deve ser maior que a data de inicio"));
        }

    }

    private void checkNameConstraints() {
        final var name = this.event.getName();
        if (name == null || name.isBlank()) {
            this.validationHandler().append(new Error("O nome do evento não pode ser vazio"));
            return;
        }

        final int length = name.trim().length();
        if (length > NAME_MAX_LENGTH || length < NAME_MIN_LENGTH) {
            this.validationHandler().append(new Error("O nome do evento deve ter entre 3 e 60 caracteres"));
        }
    }
}
