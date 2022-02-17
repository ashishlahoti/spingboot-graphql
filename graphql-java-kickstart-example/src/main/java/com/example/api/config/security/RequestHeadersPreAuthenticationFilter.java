package com.example.api.config.security;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

import javax.servlet.http.HttpServletRequest;

import static com.example.api.config.security.GraphQLSecurityConfig.USER_ID_PRE_AUTH_HEADER;

/**
 * Handle pre-authenticated authentication requests, where it is assumed that the principal has already been authenticated
 * by an external system.
 * The purpose is then only to extract the necessary information on the principal from the incoming request, rather than
 * to authenticate them.
 */
@Slf4j
public class RequestHeadersPreAuthenticationFilter extends AbstractPreAuthenticatedProcessingFilter {

    @Override
    protected Object getPreAuthenticatedPrincipal(HttpServletRequest request) {
        log.info("getPreAuthenticatedPrincipal with headers {}", request.getHeader(USER_ID_PRE_AUTH_HEADER));
        return request.getHeader(USER_ID_PRE_AUTH_HEADER);
    }

    @Override
    protected Object getPreAuthenticatedCredentials(HttpServletRequest request) {
        log.info("getPreAuthenticatedCredentials with headers {}", request.getHeader(USER_ID_PRE_AUTH_HEADER));
        return StringUtils.EMPTY;
    }

}
