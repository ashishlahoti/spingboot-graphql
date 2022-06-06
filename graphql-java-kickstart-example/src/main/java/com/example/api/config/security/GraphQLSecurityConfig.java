package com.example.api.config.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationProvider;

import javax.servlet.Filter;

@Configuration
@EnableWebSecurity // Debug = true, will print the execution of the FilterChainProxy
@EnableGlobalMethodSecurity(securedEnabled = true, prePostEnabled = true)
//@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@Slf4j
public class GraphQLSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Chapter 31: Spring Security Pre-Auth
     * When using pre-auth, you must ensure that all the graphql requests have been previously
     * authorized/authenticated by an upstream service.
     * For example, all ingress traffic to this graphql server must bypass an upstream proxy node that will validate
     * the request's JWT token. This code alone performs no authorization. Read more about Pre-auth before using this.
     */

    /**
     * Using pre-auth headers provide you the ability to switch or support other authentication
     * methods without making any/many application code changes. (E.g. JWT to something else)
     */
    public static final String USER_ID_PRE_AUTH_HEADER = "user_id";
    public static final String USER_ROLES_PRE_AUTH_HEADER = "user_roles";
    public static final String CORRELATION_ID = "correlation_ID";

    private final PreAuthenticatedAuthenticationProvider preAuthenticatedAuthenticationProvider;

    @Autowired
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(preAuthenticatedAuthenticationProvider);
        auth.inMemoryAuthentication()
                .withUser("user")
                .password("user")
                .roles("USER")
                .and()
                .withUser("manager")
                .password("manager")
                .roles("MANAGER")
                .and()
                .withUser("admin")
                .password("admin")
                .roles("ADMIN");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        log.info("@@@@@@@@@@@@@@@@@@@ Configuring spring security configure(HttpSecurity http) @@@@@@@@@@@@@@@@@@@@@@");

        // Add the Pre Authentication Filter
        http
                .addFilterBefore(createRequestHeadersPreAuthenticationFilter(), AbstractPreAuthenticatedProcessingFilter.class)
                .authorizeRequests()
                .antMatchers("/graphql")
                .hasAnyRole("USER", "MANAGER", "ADMIN")
                // Permit playground
                .antMatchers("/personaQL", "/vendor/personaQL/**")
                .hasAnyRole("USER", "MANAGER", "ADMIN")
                // Permit graphiql
                .antMatchers("/graphiql/**", "/graphql**", "/subscriptions/**", "/vendor/**", "/graphiql-subscriptions-fetcher@0.0.2/**", "/subscriptions-transport-ws@0.8.3/**")
                .hasAnyRole("USER", "MANAGER", "ADMIN")
                .antMatchers("/altair/**")
                .hasAnyRole("USER", "MANAGER", "ADMIN")
                // All endpoints require authentication
                .anyRequest().authenticated()
                .and()
                // Disable CSRF Token generation
                .csrf().disable()
                // Disable the default HTTP Basic-Auth
                .httpBasic()//.disable()
                // Disable the session management filter
                //.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                // Disable the /logout filter
                .logout().disable()
                // Disable anonymous users
                .anonymous().disable();
    }

    @Override
    public void configure(WebSecurity web) {
        log.info("@@@@@@@@@@@@@@@@@@@ Configuring spring security configure(WebSecurity web) @@@@@@@@@@@@@@@@@@@@@@");
        web.ignoring()
                // Actuator health endpoint for readiness, liveness checks etc
                .antMatchers("/actuator/health")
                // Permit playground for development
                //.antMatchers("/personaQL", "/vendor/personaQL/**")
                // Permit graphiql
                //.antMatchers("/graphql**")
                // Permit graphiql, subscriptions, etc
                //.antMatchers("/graphiql/**", "/subscriptions/**", "/vendor/**", "/graphiql-subscriptions-fetcher@0.0.2/**", "/subscriptions-transport-ws@0.8.3/**")
                // Subscription are secured via AuthenticationConnectionListener
                .antMatchers("/subscriptions")
                // Subscription are secured via AuthenticationConnectionListener
                .antMatchers("/voyager");
    }

    private Filter createRequestHeadersPreAuthenticationFilter() throws Exception {
        log.info("@@@@@@@@@@@@@@@@@@@ createRequestHeadersPreAuthenticationFilter() @@@@@@@@@@@@@@@@@@@@@@");
        var filter = new RequestHeadersPreAuthenticationFilter();
        filter.setAuthenticationDetailsSource(new GrantedAuthoritiesAuthenticationDetailsSource());
        filter.setAuthenticationManager(authenticationManager());
        filter.setContinueFilterChainOnUnsuccessfulAuthentication(false);
        return filter;
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

}
