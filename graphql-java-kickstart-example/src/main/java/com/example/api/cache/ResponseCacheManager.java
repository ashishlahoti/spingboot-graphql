package com.example.api.cache;

import com.example.api.config.security.GraphQLSecurityConfig;
import com.github.benmanes.caffeine.cache.Cache;
import graphql.kickstart.execution.input.GraphQLInvocationInput;
import graphql.kickstart.execution.input.GraphQLSingleInvocationInput;
import graphql.kickstart.servlet.cache.CachedResponse;
import graphql.kickstart.servlet.cache.GraphQLResponseCacheManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

@Slf4j
//  @Component
@RequiredArgsConstructor
public class ResponseCacheManager implements GraphQLResponseCacheManager {

    private final Cache<RequestKey, CachedResponse> responseCache;

    /**
     * Retrieve the cache by input data. If this query was not cached before, will return empty Optional.
     *
     * @param request
     * @param invocationInput
     * @return
     */
    @Override
    public CachedResponse get(HttpServletRequest request, GraphQLInvocationInput invocationInput) {
        return responseCache.getIfPresent(getRequestKey(request, invocationInput));
    }

    /**
     * Decide to cache or not this response. It depends on the implementation.
     *
     * @param request
     * @param invocationInput
     * @return
     */
    @Override
    public boolean isCacheable(HttpServletRequest request, GraphQLInvocationInput invocationInput) {
        // Do not cache introspection query
        return invocationInput.getQueries().stream().noneMatch(this::isIntrospectionQuery);
    }

    /**
     * Cache this response.
     *
     * @param request
     * @param invocationInput
     * @param cachedResponse  success response
     */
    @Override
    public void put(HttpServletRequest request, GraphQLInvocationInput invocationInput, CachedResponse cachedResponse) {
        responseCache.put(getRequestKey(request, invocationInput), cachedResponse);
    }

    /**
     * Generates the request query
     *
     * @param request
     * @param invocationInput
     * @return
     */
    private RequestKey getRequestKey(HttpServletRequest request, GraphQLInvocationInput invocationInput) {
        log.info("RequestKey >> {},  {},  {}, {}", // , {}",
                getUserId(request),
                ((GraphQLSingleInvocationInput) invocationInput).getExecutionInput().getOperationName(),
                ((GraphQLSingleInvocationInput) invocationInput).getExecutionInput().getVariables(),
                ((GraphQLSingleInvocationInput) invocationInput).getExecutionInput().getExtensions());
//                ((GraphQLSingleInvocationInput) invocationInput).getExecutionInput().getQuery());

        return new RequestKey(getUserId(request),
                ((GraphQLSingleInvocationInput) invocationInput).getExecutionInput().getQuery(),
                ((GraphQLSingleInvocationInput) invocationInput).getExecutionInput().getOperationName(),
                ((GraphQLSingleInvocationInput) invocationInput).getExecutionInput().getVariables()
        );
    }

    /**
     * Fetch the current user ID
     *
     * @param request
     * @return
     */
    private String getUserId(HttpServletRequest request) {
        var userId = request.getHeader(GraphQLSecurityConfig.USER_ID_PRE_AUTH_HEADER);
        if (userId == null) {
            // throw new IllegalArgumentException("User Id is null. Cannot read from ResponseCacheManager.");
            userId = "IDP|ignachete7";
        }
        return userId;
    }

    private boolean isIntrospectionQuery(String query) {
        return query.contains("Introspection");
    }
}
