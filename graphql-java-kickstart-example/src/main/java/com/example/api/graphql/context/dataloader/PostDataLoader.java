package com.example.api.graphql.context.dataloader;

import com.example.api.model.Post;
import com.example.api.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dataloader.MappedBatchLoader;
import org.slf4j.MDC;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

//@Configuration
@Component
@RequiredArgsConstructor
@Slf4j
public class PostDataLoader implements MappedBatchLoader<Long, List<Post>> {

    private final PostService postService;

    private final Executor myExecutor;

    @Override
    public CompletionStage<Map<Long, List<Post>>> load(Set<Long> userIds) {
        Map<String, String> mdcContext = MDC.getCopyOfContextMap();
        log.info("mdcContext: {}", mdcContext);
        return CompletableFuture.supplyAsync(() -> {
                    log.info("userIds = {}", userIds);
                    return postService.getAllPostsByUserId(userIds);
                },
                myExecutor);
    }
}
