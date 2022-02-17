package com.example.api.graphql.listener;

import graphql.kickstart.servlet.core.GraphQLServletListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.time.Instant;

@Component
@RequiredArgsConstructor
@Slf4j
public class LoggingListener implements GraphQLServletListener {

    @Override
    public RequestCallback onRequest(HttpServletRequest request, HttpServletResponse response) {
        var startTime = Instant.now();
        log.info("Received graphQL request");
        return new RequestCallback() {
            @Override
            public void onSuccess(HttpServletRequest request, HttpServletResponse response) {
                RequestCallback.super.onSuccess(request, response);
                log.info("Success on  Request, Time taken: {}", Duration.between(startTime, Instant.now()).toMillis());
            }

            @Override
            public void onError(HttpServletRequest request, HttpServletResponse response, Throwable throwable) {
                RequestCallback.super.onError(request, response, throwable);
                log.info("Error on Request, Time taken: {}", Duration.between(startTime, Instant.now()).toMillis());
            }

            @Override
            public void onFinally(HttpServletRequest request, HttpServletResponse response) {
                RequestCallback.super.onFinally(request, response);
                log.info("Completed Request, Time taken: {}", Duration.between(startTime, Instant.now()).toMillis());
            }
        };
    }
}
