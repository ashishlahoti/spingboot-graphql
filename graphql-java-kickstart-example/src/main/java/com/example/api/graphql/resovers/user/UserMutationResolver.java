package com.example.api.graphql.resovers.user;

import com.example.api.model.User;
import com.example.api.model.UserInput;
import com.example.api.service.UserService;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

/**
 * GraphQLMutationResolver to resolve Mutations (Create, Update and Delete Operations). Note the method names and return types.
 */
@Component
@RequiredArgsConstructor
@Slf4j
@Validated
public class UserMutationResolver implements GraphQLMutationResolver {

    private final UserService userService;

    public User createUser(@Valid UserInput input) {
        log.info("Creating user for {}", input);
        return userService.createUser(input);
    }

    public User updateUser(Long id, @Valid UserInput input) {
        log.info("Updating user for {}", input);
        return userService.updateUser(id, input);
    }

    public Boolean deleteUser(Long id) {
        log.info("Deleting user for {}", id);
        return userService.deleteUser(id);
    }
}
