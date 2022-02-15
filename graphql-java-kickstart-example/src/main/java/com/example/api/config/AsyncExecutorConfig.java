package com.example.api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.Executor;

/**
 * COnfiguración para la ejecución asincrona de los Resolvers
 */
@Configuration
public class AsyncExecutorConfig {

    @Bean
    public Executor myExecutor(ExecutorFactory executorFactory) {
        return executorFactory.newExecutor();
    }

}
