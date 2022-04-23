package com.example.api.graphql.resovers.comment;

import com.example.api.model.Comment;
import com.example.api.model.Post;
import com.example.api.model.User;
import com.example.api.service.PostService;
import graphql.kickstart.tools.GraphQLResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Component
@RequiredArgsConstructor
@Slf4j
public class CommentPostResolver implements GraphQLResolver<Comment> {

    private final PostService postService;
    private final Executor myExecutor;

    public CompletableFuture<Post> getPost(Comment comment) {
        return CompletableFuture.supplyAsync(
                () -> {
                    log.info("Getting post for post id {}", comment.getPostId());
                    return postService.getPostById(comment.getPostId());
                }, myExecutor);
    }

    //    Post getPost(Comment comment) {
    //        return postService.getPostById(comment.getPostId());
    //    }
}
