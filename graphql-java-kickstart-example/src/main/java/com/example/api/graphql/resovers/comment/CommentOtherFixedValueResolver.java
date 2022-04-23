package com.example.api.graphql.resovers.comment;

import com.example.api.model.Comment;
import graphql.kickstart.tools.GraphQLResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommentOtherFixedValueResolver implements GraphQLResolver<Comment> {
    private final Executor myExecutor;

    //@PreAuthorize("permitAll()")
    public CompletableFuture<String> getOtherFixedValue(Comment comment) {
        return CompletableFuture.supplyAsync(
                () -> {
                    log.info("Getting fixed value for comment id {}", comment.getId());
                    return "Fixed Value";
                }, myExecutor);
    }
}
