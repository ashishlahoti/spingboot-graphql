package com.example.api.graphql.user;

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

/**
 * Sometimes, GraphQL object can have nested GraphQL object, which result in nested queries. We implement
 * GraphQLResolver<T> to resolve such nested Queries. For e.g. User can have multiple posts [Post]
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class UserNestedResolver implements GraphQLResolver<User> {

    private final PostService postService;

    private final Executor myExecutor;

    //    List<Post> getPosts(User user) {
    //        return postService.getAllPostsByUserId(user.getId());
    //    }

    public CompletableFuture<List<Post>> getPosts(User user) {
        return CompletableFuture.supplyAsync(
                () -> {
                    log.info("Getting post for user id {}", user.getId());
                    return postService.getAllPostsByUserId(user.getId());
                }, myExecutor);
    }
}
