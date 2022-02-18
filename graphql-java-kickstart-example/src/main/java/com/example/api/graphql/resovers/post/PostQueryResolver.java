package com.example.api.graphql.resovers.post;

import com.example.api.model.Post;
import com.example.api.service.PostService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostQueryResolver implements GraphQLQueryResolver {

    private final PostService postService;

    List<Post> getPosts() {
        return postService.getAllPosts();
    }

    Post getPostById(Long postId) {
        return postService.getPostById(postId);
    }

    List<Post> getPostByUserId(Long userId) {
        return postService.getAllPostsByUserId(userId);
    }
}
