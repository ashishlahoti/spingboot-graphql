package com.example.api.graphql.user;

import com.example.api.model.Post;
import com.example.api.model.User;
import com.example.api.model.UserInput;
import com.example.api.service.PostService;
import com.example.api.service.UserService;
import graphql.GraphQL;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMutationResolver implements GraphQLMutationResolver {
    private final UserService userService;

    User createUser(UserInput input) {
        return userService.createUser(input);
    }
    User updateUser(UserInput input) {
        return userService.updateUser(input);
    }
    Boolean deleteUser(Long id) {
        return userService.deleteUser(id);
    }
}
