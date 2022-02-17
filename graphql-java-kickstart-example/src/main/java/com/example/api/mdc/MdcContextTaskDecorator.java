package com.example.api.mdc;

import graphql.kickstart.servlet.AsyncTaskDecorator;
import org.slf4j.MDC;
import org.springframework.core.task.TaskDecorator;
import org.springframework.stereotype.Component;

import static com.example.api.instrumentation.RequestLoggingInstrumentation.CORRELATION_ID;

/**
 * A decorator to be applied to any Runnable about to be executed.
 */
@Component
public class MdcContextTaskDecorator implements AsyncTaskDecorator, TaskDecorator {

    /**
     * Propagate the current thread's MDC context to the target thread.
     */
    @Override
    public Runnable decorate(Runnable runnable) {
        //var mdcContext = MDC.getCopyOfContextMap();
        var correlationId = MDC.get(CORRELATION_ID);
        return () -> {
            try {
                MDC.put(CORRELATION_ID, correlationId);
                //MDC.setContextMap(mdcContext);
                runnable.run();
            } finally {
                //MDC.clear();
                MDC.remove(CORRELATION_ID);
            }
        };
    }
}
