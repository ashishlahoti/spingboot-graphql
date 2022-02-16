package com.example.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Configuración para la ejecución asincrona de los Resolvers
 */
@Configuration
public class AsyncExecutorConfig {

    @Bean
    public Executor myExecutor(ExecutorFactory executorFactory) {
        return executorFactory.newExecutor();
    }

    @Bean
    public Executor otherExecutor() {
        return Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    }
}
