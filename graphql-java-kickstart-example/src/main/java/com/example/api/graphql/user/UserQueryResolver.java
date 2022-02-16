package com.example.api.graphql.user;

import com.example.api.model.User;
import com.example.api.service.UserService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.SelectedField;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.logging.Logger;

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
@Slf4j
public class UserQueryResolver implements GraphQLQueryResolver {

    private final UserService userService;

    List<User> getUsers() {
        return userService.getAllUsers();
    }

    /**
     *
     * @param userId
     * @param env A DataFetchingEnvironment instance of passed to a DataFetcher as a execution context and its the place
     *            where you can find out information to help you resolve a data value given a graphql field input
     * @return User
     */
    User getUserById(Long userId, DataFetchingEnvironment env) {
        // Selection fields set requested
        env.getSelectionSet().getFields().stream().map(SelectedField::getName).distinct().forEach(log::info);
        // Request variables
        env.getVariables().entrySet().stream().map(Object::toString).forEach(log::info);
        // Request arguments, filter arguments
        env.getArguments().entrySet().stream().map(Object::toString).forEach(log::info);

        return userService.getUserById(userId);
    }
}
