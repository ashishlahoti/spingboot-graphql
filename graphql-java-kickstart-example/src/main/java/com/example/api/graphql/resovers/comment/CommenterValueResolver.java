package com.example.api.graphql.resovers.comment;

import com.example.api.model.Comment;
import com.example.api.model.Commenter;
import graphql.kickstart.tools.GraphQLResolver;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.SelectedField;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

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
    private final Executor myExecutor;

    /**
     * @param comment data class instance is passed as the first argument to the resolver function
     * @return
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<Commenter> getCommenter(Comment comment, DataFetchingEnvironment dataFetchingEnvironment) {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();

        log.info("authentication: {},", authentication);
        log.info("Variables: {},", dataFetchingEnvironment.getVariables().keySet());
        log.info("Arguments: {},", dataFetchingEnvironment.getArguments().keySet());
        // log.info("Document: {},", dataFetchingEnvironment.getDocument());
        log.info("SelectionSet: {},", dataFetchingEnvironment.getSelectionSet().getFields().stream().map(SelectedField::getName).toList());

        if(comment.getCommenter() != null) {
            log.info("IF Getting commenter: {},", comment.getCommenter());
            return CompletableFuture.completedFuture(comment.getCommenter());
        }

        // WILL CALL the Dataloader
        return CompletableFuture.supplyAsync(
                () -> {
                    log.info("ELSE CALL the Dataloader to fetch the commenter for comment id {}", comment.getId());
                    Commenter nacho_martin = new Commenter(1L, "Nacho Martin", "jose-ignacio.martin@klarna.com");
                    log.info("ELSE Commenter fetched from the data loader {}", nacho_martin);
                    return nacho_martin;
                }, myExecutor);
    }
}
