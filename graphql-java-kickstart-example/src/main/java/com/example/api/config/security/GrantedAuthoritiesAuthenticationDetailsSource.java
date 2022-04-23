package com.example.api.config.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationDetailsSource;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;

import static com.example.api.config.security.GraphQLSecurityConfig.USER_ROLES_PRE_AUTH_HEADER;

/**
 * This AuthenticationDetails implementation allows for storing a list of pre-authenticated Granted Authorities.
 */
@Slf4j
public class GrantedAuthoritiesAuthenticationDetailsSource implements AuthenticationDetailsSource<HttpServletRequest, PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails> {

    /**
     * Called by a class when it wishes a new authentication details instance to be created.
     *
     * @param request
     * @return
     */
    @Override
    public PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails buildDetails(HttpServletRequest request) {
        log.info("@@@@@@@@@@@@@@@@@@@ buildDetails() @@@@@@@@@@@@@@@@@@@@@@");
        // Fetch the user roles from the header
        var userRoles = request.getHeader(USER_ROLES_PRE_AUTH_HEADER);

        log.info("userRoles: {}", userRoles);

        // Fetch the authorities based on the user roles from the header
        var authorities = GrantedAuthorityFactory.getAuthoritiesFrom(userRoles);

        log.info("authorities: {}", authorities);
        // WebAuthenticationDetails implementation allows for storing a list of pre-authenticated Granted Authorities
        return new PreAuthenticatedGrantedAuthoritiesWebAuthenticationDetails(request, authorities);
    }
}
