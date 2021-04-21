package com.example.api.datafetcher;

import com.example.api.model.Post;
import com.example.api.model.User;
import com.example.api.service.PostService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.DgsQuery;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@DgsComponent
@RequiredArgsConstructor
public class PostDataFetcher {

    private final PostService postService;

    @DgsQuery
    public List<Post> posts() {
        return postService.getAllPosts();
    }

    @DgsQuery
    public Post postById(String id) {
        return postService.getPostById(Long.parseLong(id));
    }

    @DgsQuery
    public List<Post> postsByUserId(String userId) {
        return postService.getAllPostsByUserId(Long.parseLong(userId));
    }

    @DgsData(parentType = "User")
    public CompletableFuture<List<Post>> posts(DgsDataFetchingEnvironment dfe) {
        User user = dfe.getSource();
        return CompletableFuture.supplyAsync(() -> postService.getAllPostsByUserId(user.getId()));
    }
}
