package com.example.api.graphql.user;

import com.example.api.model.Post;
import com.example.api.model.User;
import com.example.api.service.PostService;
import graphql.kickstart.tools.GraphQLResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Sometimes, GraphQL object can have nested GraphQL object, which result in nested queries. We implement
 * GraphQLResolver<T> to resolve such nested Queries. For e.g. User can have multiple posts [Post]
 */
@Component
@RequiredArgsConstructor
public class UserPostResolver implements GraphQLResolver<User> {

    private final PostService postService;

    List<Post> getPosts(User user) {
        return postService.getAllPostsByUserId(user.getId());
    }
}
