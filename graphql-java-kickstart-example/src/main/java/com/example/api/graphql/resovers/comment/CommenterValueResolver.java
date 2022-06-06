package com.example.api.graphql.resovers.comment;

import com.example.api.model.Comment;
import com.example.api.model.Commenter;
import graphql.kickstart.tools.GraphQLResolver;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.SelectedField;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dataloader.DataLoader;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static com.example.api.graphql.context.dataloader.DataLoaderRegistryFactory.COMMENTER_DATA_LOADER;

/**
 * When given a BookResolver instance, GraphQL Java Tools first attempts to map fields to methods on the resolver before mapping them
 * to fields or methods on the data class. If there is a matching method on the resolver, the data class instance is passed as the first
 * argument to the resolver function.
 * This does not apply to root resolvers, since those don’t have a data class to resolve for. An optional parameter can be defined to
 * inject the DataFetchingEnvironment, and must be the last argument.
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class CommenterValueResolver implements GraphQLResolver<Comment> {

    /**
     * @param comment data class instance is passed as the first argument to the resolver function
     * @return
     */
    @PreAuthorize("hasAnyRole('ROLE_MANAGER', 'ROLE_ADMIN', 'ROLE_USER')")
    public CompletableFuture<Commenter> getCommenter(Comment comment, DataFetchingEnvironment env) {
        //printAuthenticationAndEnv(env);
        Optional<Commenter> commentOptional = Optional.ofNullable(comment.getCommenter());

        if (commentOptional.isPresent()) {
            log.info("IF Getting commenter FROM PARENT COMMENT: {},", comment.getCommenter());
            return CompletableFuture.completedFuture(comment.getCommenter());
        }

        // WILL CALL the Dataloader
        log.info("ELSE CALL the Dataloader to fetch the commenter for comment id {}", comment.getId());
        DataLoader<Long, Commenter> dataLoader = env.getDataLoader(COMMENTER_DATA_LOADER);
        return dataLoader.load(comment.getId(), comment);
    }

    private void printAuthenticationAndEnv(DataFetchingEnvironment env) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        log.info("authentication: {},", authentication);
        log.info("Variables: {},", env.getVariables().keySet());
        log.info("Arguments: {},", env.getArguments().keySet());
        // log.info("Document: {},", dataFetchingEnvironment.getDocument());
        log.info("SelectionSet: {},", env.getSelectionSet().getFields().stream().map(SelectedField::getName).toList());
        // log.info("Source: {},", (Object) env.getSource());
        // log.info("Root: {},", (Object) env.getRoot());
        // log.info("FieldDefinition: {},", env.getFieldDefinition());
    }
}
