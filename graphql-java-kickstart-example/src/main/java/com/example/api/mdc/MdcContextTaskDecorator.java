package com.example.api.mdc;

import graphql.kickstart.servlet.AsyncTaskDecorator;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;
import org.springframework.stereotype.Component;

/**
 * A decorator to be applied to any Runnable about to be executed.
 */
@Component
@Slf4j
public class MdcContextTaskDecorator implements AsyncTaskDecorator, TaskDecorator {

    /**
     * Propagate the current thread's MDC context to the target thread.
     */
    @Override
    public Runnable decorate(Runnable runnable) {
        // Lo usabamos al poner ahora el correlation ID en el CustomGraphQLContextBuilder
        var mdcContext = MDC.getCopyOfContextMap();
        log.info("mdcContext: {}", mdcContext);
        //log.info("MDC CORRELATION_ID: {}", MDC.get(CORRELATION_ID));
        //log.info("MDC SECURITY_CONTEXT: {}", MDC.get(SECURITY_CONTEXT));
        // Lo usabamos cuando poniamos el correlation ID en el RequestLoggingInstrumentation
        // var correlationId = MDC.get(CORRELATION_ID);
        return () -> {
            try {
                // MDC.put(CORRELATION_ID, correlationId);
                MDC.setContextMap(mdcContext);
                runnable.run();
            } catch (Exception e) {
                log.error("decorate exception: {}", e.getMessage());
            } finally {
                MDC.clear();
                // MDC.remove(CORRELATION_ID);
            }
        };
    }
}
