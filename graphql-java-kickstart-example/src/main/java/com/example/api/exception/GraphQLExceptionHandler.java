package com.example.api.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import graphql.GraphqlErrorBuilder;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.kickstart.spring.error.ThrowableGraphQLError;
import graphql.schema.CoercingParseValueException;
import graphql.schema.DataFetchingEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolationException;
import java.time.format.DateTimeParseException;

@Component
@Slf4j
public class GraphQLExceptionHandler {

    @ExceptionHandler({
            GraphQLException.class,
            ConstraintViolationException.class,
            IllegalArgumentException.class,
            CoercingParseValueException.class,
            InvalidFormatException.class,
            DateTimeParseException.class
    })
    public ThrowableGraphQLError handle(Exception e) {
        log.warn("handle Exception: {}", e.getMessage());
        return new ThrowableGraphQLError(e);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ThrowableGraphQLError handle(AccessDeniedException e) {
        log.warn("handle AccessDeniedException: {}", e.getMessage());
        GraphqlErrorBuilder.newError().message(e.getMessage()).build();
        return new ThrowableGraphQLError(e, HttpStatus.FORBIDDEN.getReasonPhrase());
    }

    @ExceptionHandler(RuntimeException.class)
    public ThrowableGraphQLError handle(RuntimeException e) {
        log.warn("handle RuntimeException: {}", e.getMessage());
        return new ThrowableGraphQLError(e, HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());
    }
}
