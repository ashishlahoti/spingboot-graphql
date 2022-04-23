package com.example.api.graphql.resovers.comment;

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

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommenterFieldsValueResolver implements GraphQLResolver<Commenter> {
    private final Executor myExecutor;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<Long> getId(Commenter commenter, DataFetchingEnvironment dataFetchingEnvironment) {

        printAuthentication();

        if (commenter != null) {
            log.info("IF Getting commenter Id: {},", commenter.getId());
            return CompletableFuture.completedFuture(commenter.getId());
        }

        // Dataloader call NO NEEDED because the parent resolver call the dataLoader. **** REMOVE
        return CompletableFuture.supplyAsync(
                () -> {
                    log.info("ELSE Getting commenter id @@@@@@");
                    return 1L;
                }, myExecutor);
    }



    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<String> getName(Commenter commenter, DataFetchingEnvironment dataFetchingEnvironment) {
        printAuthentication();

        if (commenter != null) {
            log.info("IF Getting commenter Name: {},", commenter.getName());
            return CompletableFuture.completedFuture(commenter.getName());
        }

        // Dataloader call NO NEEDED because the parent resolver call the dataLoader. **** REMOVE
        return CompletableFuture.supplyAsync(
                () -> {
                    log.info("ELSE Getting commenter Name @@@@@@");
                    return "Nacho Martin";
                }, myExecutor);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public CompletableFuture<String> getEmail(Commenter commenter, DataFetchingEnvironment dataFetchingEnvironment) {

        printAuthentication();

        if (commenter != null) {
            log.info("IF Getting commenter Email: {},", commenter.getEmail());
            return CompletableFuture.completedFuture(commenter.getEmail());
        }

        // Dataloader call NO NEEDED because the parent resolver call the dataLoader. **** REMOVE
        return CompletableFuture.supplyAsync(
                () -> {
                    log.info("ELSE Getting commenter Email @@@@@@");
                    return "jose-ignacio.martin@klarna.com";
                }, myExecutor);
    }


    private void printAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication authentication = context.getAuthentication();
        log.info("authentication: {},", authentication);
    }
}
