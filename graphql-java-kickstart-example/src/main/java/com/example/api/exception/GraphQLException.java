package com.example.api.exception;

public class GraphQLException extends RuntimeException {
    public GraphQLException(String message) {
        super(message);
    }
    public GraphQLException(String message, Throwable cause) {
        super(message, cause);
    }
}
