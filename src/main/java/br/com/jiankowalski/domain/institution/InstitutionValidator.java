package br.com.jiankowalski.domain.institution;

import br.com.jiankowalski.domain.validation.Error;
import br.com.jiankowalski.domain.validation.ValidationHandler;
import br.com.jiankowalski.domain.validation.Validator;

class InstitutionValidator extends Validator {

    public static final int NAME_MAX_LENGTH = 20;
    public static final int NAME_MIN_LENGTH = 5;

    private final Institution institution;

    protected InstitutionValidator(final Institution aInstitution, final ValidationHandler aHandler) {
        super(aHandler);
        this.institution = aInstitution;
    }

    @Override
    public void validate() {
        checkNameConstraints();
    }

    private void checkNameConstraints() {
        final var name = this.institution.getName();
        if (name == null || name.isBlank()) {
            this.validationHandler().append(new Error("O nome da instituição não pode ser vazio"));
            return;
        }

        final int length = name.trim().length();
        if (length > NAME_MAX_LENGTH || length < NAME_MIN_LENGTH) {
            this.validationHandler().append(new Error("O nome da instituição deve ter entre 5 e 20 caracteres"));
        }
    }
}
