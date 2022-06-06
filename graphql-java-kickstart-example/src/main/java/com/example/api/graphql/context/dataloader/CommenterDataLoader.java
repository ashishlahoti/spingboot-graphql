package com.example.api.graphql.context.dataloader;

import com.example.api.model.Commenter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dataloader.MappedBatchLoader;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;

//@Configuration
@Component
@RequiredArgsConstructor
@Slf4j
public class CommenterDataLoader implements MappedBatchLoader<Long, Commenter> {

    private final Executor myExecutor;

    @Override
    public CompletionStage<Map<Long, Commenter>> load(Set<Long> commentIds) {
        Map<String, String> mdcContext = MDC.getCopyOfContextMap();
        log.info("mdcContext: {}", mdcContext);
        return CompletableFuture.supplyAsync(() -> {
                    log.info("commentIds = {}", commentIds);
                    Map<Long, Commenter> result = new HashMap<>();
                    for (Long commentId : commentIds) {
                        result.put(commentId, new Commenter(1L, "Nacho Martin", "jose-ignacio.martin@klarna.com", true));
                    }
                    log.info("result: {}", result);
                    return result;
                },
                myExecutor);
    }
}
