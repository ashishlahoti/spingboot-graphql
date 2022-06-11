package com.example.api.config.graphQL.directives;

import graphql.language.StringValue;
import graphql.schema.DataFetcher;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLFieldsContainer;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Objects;

@Slf4j
public class AuthorisationDirective implements SchemaDirectiveWiring {


    @Override
    public GraphQLFieldDefinition onField(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> environment) {

        String targetAuthRole = ((StringValue) Objects.requireNonNull(environment.getDirective().getArgument("role")
                .getArgumentValue().getValue())).getValue();

        GraphQLFieldDefinition field = environment.getElement();
        GraphQLFieldsContainer parentType = environment.getFieldsContainer();

        log.info(">>>>>>>>>  field: {}, parentType: {}, Role: {}", field.getName(), parentType.getName(), targetAuthRole);

        // build a data fetcher that first checks authorisation roles before then calling the original data fetcher
        DataFetcher<?> originalDataFetcher = environment.getCodeRegistry().getDataFetcher(parentType, field);
        DataFetcher<?> authDataFetcher = dfe -> {
            if (hasRole(targetAuthRole)) {
                return originalDataFetcher.get(dfe);
            } else {
                throw new AccessDeniedException("No access permission");
            }
        };

        // now change the field definition to have the new authorising data fetcher
        environment.getCodeRegistry().dataFetcher(parentType, field, authDataFetcher);
        return field;
    }

    private boolean hasRole(String targetAuthRole) {
        SecurityContext sc = SecurityContextHolder.getContext();
        Authentication authentication = sc.getAuthentication();
        log.info(">>>>>>>>>  AUTHORITIES: {}", authentication.getAuthorities());
        List<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .toList();
        return roles.contains(targetAuthRole);
    }
}
