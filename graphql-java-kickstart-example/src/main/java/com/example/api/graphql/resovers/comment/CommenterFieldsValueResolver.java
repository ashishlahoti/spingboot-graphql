package com.example.api.graphql.resovers.comment;

import com.example.api.model.Comment;
import com.example.api.model.Commenter;
import graphql.kickstart.tools.GraphQLResolver;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommenterFieldsValueResolver implements GraphQLResolver<Commenter> {

    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN', 'ROLE_USER')")
    public Long getId(Commenter commenter) {
        log.info("Getting commenter Id: {},", commenter.getId());
        return commenter.getId();
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN', 'ROLE_USER')")
    public String getName(Commenter commenter) {
        log.info("Getting commenter Name: {},", commenter.getName());
        return commenter.getName();
    }

    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN', 'ROLE_USER')")
    public String getEmail(Commenter commenter) {
        log.info("Getting commenter Email: {},", commenter.getEmail());
        return commenter.getEmail();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    public boolean isPublish(Commenter commenter, DataFetchingEnvironment env) {
        //printAuthentication(env);
        log.info("Getting commenter publish: {},", commenter.isPublish());
        return commenter.isPublish();
    }

    private void printAuthentication(DataFetchingEnvironment env) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        log.info("authentication: {},", authentication);
        log.info("Source: {},", (Object) env.getSource());
        log.info("Root: {},", env.getRoot().getClass());
        log.info("FieldDefinition: {},", env.getFieldDefinition());
        log.info("Selections: {},", env.getOperationDefinition().getSelectionSet().getSelections());
    }
}
