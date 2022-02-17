package com.example.api.graphql.context.dataloader;

import com.example.api.model.Post;
import com.example.api.model.User;
import com.example.api.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dataloader.BatchLoaderEnvironment;
import org.dataloader.DataLoader;
import org.dataloader.DataLoaderFactory;
import org.dataloader.DataLoaderRegistry;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataLoaderRegistryFactory {

    public static final String POST_DATA_LOADER = "POST_DATA_LOADER";

    private final PostService postService;
    private final Executor myExecutor;

    public DataLoaderRegistry create(String userId) {
        var registry = new DataLoaderRegistry();
        registry.register(POST_DATA_LOADER, createPostDataLoader(userId));
        return registry;
    }

    //    private DataLoader<Long, List<Post>> createPostDataLoader(String userId) {
    //        return DataLoaderFactory.newMappedDataLoader(userIds, env ->
    //                CompletableFuture.supplyAsync(() -> {
    //                            log.info("userIds = {}", userIds);
    //                            log.info("getKeyContexts = {}", env.getKeyContexts());
    //                            log.info("getKeyContextsList = {}", env.getKeyContextsList());
    //                            return postService.getAllPostsByUserId((Map) env.getKeyContexts(), userId);
    //                        },
    //                        otherExecutor));
    //    }

    //    private DataLoader<Long, List<Post>> createPostDataLoader(String userId) {
    //        return DataLoaderFactory.newMappedDataLoader(userIds, env ->
    //                CompletableFuture.supplyAsync(() -> {
    //                            log.info("userIds = {}", userIds);
    //                            log.info("getKeyContexts = {}", env.getKeyContexts());
    //                            log.info("getKeyContextsList = {}", env.getKeyContextsList());
    //                            return postService.getAllPostsByUserId((List) env.getKeyContextsList(), userId);
    //                        },
    //                        myExecutor));
    //    }

    private DataLoader<Long, List<Post>> createPostDataLoader(String userId) {
        return DataLoaderFactory.newMappedDataLoader((Set<Long> userIds) ->
                CompletableFuture.supplyAsync(() -> postService.getAllPostsByUserId(userIds, userId), myExecutor));
    }
}
