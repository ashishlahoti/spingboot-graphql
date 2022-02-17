package com.example.api.instrumentation;

import graphql.ExecutionResult;
import graphql.execution.instrumentation.InstrumentationContext;
import graphql.execution.instrumentation.SimpleInstrumentation;
import graphql.execution.instrumentation.SimpleInstrumentationContext;
import graphql.execution.instrumentation.parameters.InstrumentationExecutionParameters;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.time.Clock;
import java.time.Duration;
import java.time.Instant;

/**
 * An implementation of SimpleInstrumentation that logs the execution Id and execution duration.
 * SimpleInstrumentation: It can be used as a base for derived classes where you only implement the methods you want to
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class RequestLoggingInstrumentation extends SimpleInstrumentation {

    public static final String CORRELATION_ID = "correlation_ID";
    /**
     * This is called right at the start of query execution and its the first step in the instrumentation chain.
     * @param parameters
     * @return
     */
    @Override
    public InstrumentationContext<ExecutionResult> beginExecution(InstrumentationExecutionParameters parameters) {
        var start = Instant.now();
        var executionId = parameters.getExecutionInput().getExecutionId();

        // MDC class hides and serves as a substitute for the underlying logging system's MDC implementation.
        // Put a diagnostic context value (the val parameter) as identified with the key parameter into the current
        // thread's diagnostic context map. The key parameter cannot be null. The val parameter can be null only if
        // the underlying implementation supports it.
        MDC.put(CORRELATION_ID, executionId.toString());

        log.info("Operation: {} with variables: {}", parameters.getOperation(), parameters.getVariables());
        return SimpleInstrumentationContext.whenCompleted((executionResult, throwable) -> {
            // This callback will occur in the resolver thread.
            var duration = Duration.between(start, Instant.now());
            if (throwable == null) {
                log.info("Completed successfully in: {}", duration.toMillis());
            } else {
                log.warn("Failed in: {}", duration.toMillis(), throwable);
            }
        });
    }

}
