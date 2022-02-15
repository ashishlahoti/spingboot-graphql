package com.example.api.graphql.user;

import com.example.api.model.User;
import com.example.api.service.UserService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * we implement GraphQLQueryResolver to resolve Queries (Read Operations). Note that the method names (users, userById)
 * and return types (User) are same in GraphQL schema and QueryResolver class.
 * The names of the method must be one of the following, in this order:
 * - <field>
 * - is<field> – only if the field is of type Boolean
 * - get<field>
 */
@Component
@RequiredArgsConstructor
public class UserQueryResolver implements GraphQLQueryResolver {

    private final UserService userService;

    List<User> getUsers() {
        return userService.getAllUsers();
    }

    User getUserById(Long userId) {
        return userService.getUserById(userId);
    }
}
