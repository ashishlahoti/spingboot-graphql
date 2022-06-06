package com.example.api.config.async;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.core.task.TaskDecorator;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.security.task.DelegatingSecurityContextAsyncTaskExecutor;
import org.springframework.stereotype.Component;

import java.util.concurrent.Executor;

@Component
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public final class ExecutorFactory {

    private final TaskDecorator mdcContextTaskDecorator;

    public Executor newExecutor() {
        var executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(Runtime.getRuntime().availableProcessors());
        executor.setMaxPoolSize(Runtime.getRuntime().availableProcessors());
        executor.setKeepAliveSeconds(0);
        executor.setTaskDecorator(mdcContextTaskDecorator); // Specify a custom TaskDecorator to be applied to any Runnable about to be executed.
        executor.initialize();
        /**
         * An AsyncTaskExecutor which wraps each Runnable in a DelegatingSecurityContextRunnable and each Callable in a DelegatingSecurityContextCallable.
         * Necesaria para que pase las credenciales de seguridad del usuario autenticado en procesos asincronos
         */
        return new DelegatingSecurityContextAsyncTaskExecutor(executor);
    }
}
