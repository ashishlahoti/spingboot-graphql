package com.example.api.config.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.example.api.config.security.GraphQLSecurityConfig.USER_ID_PRE_AUTH_HEADER;

/**
 * Returns the List  of SimpleGrantedAuthority based on the userRoles
 */

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Slf4j
public class GrantedAuthorityFactory {

    /**
     * Returns the List  of SimpleGrantedAuthority based on the userRoles
     */
    public static List<SimpleGrantedAuthority> getAuthoritiesFrom(String userRoles) {
        log.info("getAuthoritiesFrom with userRoles {}", userRoles);
        if (StringUtils.isBlank(userRoles)) {
            return List.of();
        }

        return Stream.of(userRoles.split(","))
                .map(SimpleGrantedAuthority::new)
                .toList();
    }

}
