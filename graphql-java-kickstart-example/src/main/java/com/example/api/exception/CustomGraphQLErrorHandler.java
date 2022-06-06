package com.example.api.exception;

import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.ExceptionWhileDataFetching;
import graphql.GraphQLError;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.kickstart.execution.error.GraphQLErrorHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Handler to handle the returned list of errors. the default implementation is the DefaultGraphQLErrorHandler
 */
@Component
@Slf4j
public class CustomGraphQLErrorHandler implements GraphQLErrorHandler {
    @Override
    public List<GraphQLError> processErrors(List<GraphQLError> errors) {
        log.info("processErrors Errors:  {}", errors);

        //        if (!errors.isEmpty()) {
        //            errors.stream().forEach(graphQLError -> {
        //                log.info("Error type:  {}", graphQLError.getErrorType());
        //                log.info("Error message:  {}", graphQLError.getMessage());
        //                log.info("Error path:  {}", graphQLError.getPath());
        //                log.info("Error locations:  {}", graphQLError.getLocations());
        //            });
        //        }
        return errors;
        //return errors.stream().map(this::getNested).toList();
    }

    private GraphQLError getNested(GraphQLError error) {
        if (error instanceof RuntimeException) {
            ExceptionWhileDataFetching exceptionError = (ExceptionWhileDataFetching) error;
            if (exceptionError.getException() instanceof GraphQLError) {
                return (GraphQLError) exceptionError.getException();
            }
        }
        return error;
    }
}
