package com.example.api.graphql.resovers.user;

import com.example.api.graphql.context.dataloader.DataLoaderRegistryFactory;
import com.example.api.model.Post;
import com.example.api.model.User;
import com.example.api.service.PostService;
import graphql.kickstart.tools.GraphQLResolver;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dataloader.DataLoader;
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
public class UserPostsResolver implements GraphQLResolver<User> {

    private final PostService postService;

    private final Executor myExecutor;

    //    List<Post> getPosts(User user) {
    //        return postService.getAllPostsByUserId(user.getId());
    //    }

    /**
     * Ejecuta las peticiones en paralelo
     *
     * @param user
     * @return
     */
    //    public CompletableFuture<List<Post>> getPosts(User user,  DataFetchingEnvironment env) {
    //        // Request variables
    //        log.info("getPosts >>>>> Variables:");
    //        env.getVariables().entrySet().stream().map(Object::toString).forEach(log::info);
    //        // Request arguments, filter arguments
    //        log.info("getPosts >>>>> Arguments:");
    //        env.getArguments().entrySet().stream().map(Object::toString).forEach(log::info);
    //        return CompletableFuture.supplyAsync(
    //                () -> {
    //                    log.info("Getting post for user id {}", user.getId());
    //                    return postService.getAllPostsByUserId(user.getId());
    //                }, myExecutor);
    //    }

    /**
     * Ejecuta las peticiones en paralelo y le pasamos los ids y el objeto User
     * @param user
     * @param environment
     * @return
     */
    public CompletableFuture<List<Post>> getPosts(User user, DataFetchingEnvironment environment) {
        log.info("getPosts - Getting post for user id: {}", user.getId());
        //        DataLoader<Long, List<Post>> dataLoader = environment.getDataLoader(DataLoaderRegistryFactory.POST_DATA_LOADER);
        //        return dataLoader.load(user.getId(), user);
        DataLoader<Long, List<Post>> dataLoader2 = environment.getDataLoader(DataLoaderRegistryFactory.POST_DATA_LOADER_2);
        return dataLoader2.load(user.getId(), user);
    }

    /**
     * Ejecuta las peticiones en paralelo y le pasamos los ids
     * @param user
     * @param environment
     * @return
     */
    //    public CompletableFuture<List<Post>> getPosts(User user, DataFetchingEnvironment environment) {
    //        log.info("getPosts - Getting post for user id: {}", user.getId());
    //        DataLoader<Long, List<Post>> dataLoader = environment.getDataLoader(DataLoaderRegistryFactory.POST_DATA_LOADER);
    //        return dataLoader.load(user.getId());
    //    }

    /**
     * Ejecuta las peticiones en paralelo
     *
     * @param user
     * @return
     */
    public CompletableFuture<List<Post>> getPostsById(User user, Long postId) {
        return CompletableFuture.supplyAsync(
                () -> {
                    log.info("Getting post for user id {} with post id {}", user.getId(), postId);
                    return postService.getAllPostsByUserId(user.getId());
                }, myExecutor);
    }


}
