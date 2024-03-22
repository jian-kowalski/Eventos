package br.com.jiankowalski.infrastructure.api.exceptionhandler;

import br.com.jiankowalski.domain.exceptions.DomainException;
import br.com.jiankowalski.domain.validation.Error;

import java.util.List;

record ApiError(String message, List<Error> errors) {
    static ApiError from(final DomainException ex) {
        return new ApiError(ex.getMessage(), ex.getErrors());
    }
}
