package com.example.api.graphql.context;

import com.example.api.graphql.context.dataloader.DataLoaderRegistryFactory;
import graphql.kickstart.execution.context.GraphQLContext;
import graphql.kickstart.servlet.context.DefaultGraphQLServletContext;
import graphql.kickstart.servlet.context.DefaultGraphQLWebSocketContext;
import graphql.kickstart.servlet.context.GraphQLServletContextBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.Session;
import javax.websocket.server.HandshakeRequest;
import java.util.Optional;
import java.util.UUID;

import static com.example.api.graphql.instrumentation.RequestLoggingInstrumentation.CORRELATION_ID;
import static com.example.api.graphql.instrumentation.RequestLoggingInstrumentation.SECURITY_CONTEXT;

/**
 * Custom GraphQLServletContextBuilder
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class CustomGraphQLContextBuilder implements GraphQLServletContextBuilder {

    private final DataLoaderRegistryFactory dataLoaderRegistryFactory;

    /**
     * Return a CustomGraphQLContext
     *
     * @param httpServletRequest
     * @param httpServletResponse
     * @return
     */
    @Override
    public GraphQLContext build(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {

        /**
         * NACHO
         * Relacionado con el Contexto e incluir el correlation Id in los logs
         */
        MDC.put(CORRELATION_ID, UUID.randomUUID().toString());
        MDC.put(SECURITY_CONTEXT, Optional.ofNullable(SecurityContextHolder.getContext().getAuthentication()).map(Authentication::getName).orElse(""));

        log.info("Principal: {}", SecurityContextHolder.getContext().getAuthentication());
        log.info("MDC CORRELATION_ID: {}", MDC.get(CORRELATION_ID));
        log.info("MDC SECURITY_CONTEXT: {}", MDC.get(SECURITY_CONTEXT));
        log.info("CopyOfContextMap: {}", MDC.getCopyOfContextMap());

        var userId = httpServletRequest.getHeader("user_id");
        log.info("userId: {}", userId);

        DefaultGraphQLServletContext defaultGraphQLServletContext = DefaultGraphQLServletContext.createServletContext()
                .with(httpServletRequest)
                .with(httpServletResponse)
                .with(dataLoaderRegistryFactory.create(userId))
                .build();

        //return new CustomGraphQLContext(userId, context);
        return defaultGraphQLServletContext;
    }

    @Override
    public GraphQLContext build(Session session, HandshakeRequest handshakeRequest) {
        var userId = handshakeRequest.getHeaders().get("user_id").get(0);
        log.info("userId: {}", userId);

        return DefaultGraphQLWebSocketContext.createWebSocketContext()
                .with(session)
                .with(handshakeRequest)
                .with(dataLoaderRegistryFactory.create(userId))
                .build();
    }

    @Override
    public GraphQLContext build() {
        throw new IllegalStateException("Unsupported");
    }

}
