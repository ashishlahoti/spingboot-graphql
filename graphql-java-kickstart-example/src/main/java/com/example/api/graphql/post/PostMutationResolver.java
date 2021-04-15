package com.example.api.graphql.post;

import com.example.api.model.Post;
import com.example.api.service.PostService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostMutationResolver implements GraphQLMutationResolver {

    private final PostService postService;

    Post createPost(Post input) {
        return postService.createPost(input);
    }

}
