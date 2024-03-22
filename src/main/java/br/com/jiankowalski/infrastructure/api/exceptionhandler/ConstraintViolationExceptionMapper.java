package br.com.jiankowalski.infrastructure.api.exceptionhandler;

import br.com.jiankowalski.domain.exceptions.DomainException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;
import jakarta.ws.rs.ext.Provider;

@Provider
public class ConstraintViolationExceptionMapper implements ExceptionMapper<DomainException> {

    @Override
    public Response toResponse(DomainException ex) {
        return Response.status(422)
                .type(MediaType.APPLICATION_JSON)
                .entity(new ApiError(ex.getMessage(), ex.getErrors())).build();
    }


}