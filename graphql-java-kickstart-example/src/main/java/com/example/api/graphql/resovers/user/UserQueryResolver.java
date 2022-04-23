package com.example.api.graphql.resovers.user;

import com.example.api.graphql.context.CustomGraphQLContext;
import com.example.api.model.Post;
import com.example.api.model.User;
import com.example.api.service.UserService;
import graphql.kickstart.tools.GraphQLQueryResolver;
import graphql.relay.*;
import graphql.schema.DataFetchingEnvironment;
import graphql.schema.SelectedField;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

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

    private final Executor myExecutor;

    // @PreAuthorize("hasAuthority('get:users')")
    //    List<User> getUsers() {
    //        return userService.getAllUsers();
    //    }

    public CompletableFuture<List<User>> getUsers() {
        return CompletableFuture.supplyAsync(
                () -> {
                    return userService.getAllUsers();
                }, myExecutor);
    }

    // @PreAuthorize("hasAuthority('get:users')")
    public Connection<User> getUsersPaginated(int number, @Nullable String cursor) {
        log.info("getUsersPaginated - Get next {} users paginated from cursor {}", number, cursor);
        List<Edge<User>> edges = getUsersWithCursor(cursor).stream()
                .map(user -> (Edge<User>) new DefaultEdge<>(user, new DefaultConnectionCursor(user.getId().toString())))
                .limit(number)
                .toList();

        log.info("getUsersPaginated - edges {}", edges);
        ConnectionCursor firstCursor = edges.isEmpty() ? null : edges.get(0).getCursor();
        ConnectionCursor lastCursor = edges.isEmpty() ? null : edges.get(edges.size() - 1).getCursor();
        log.info("getUsersPaginated - firstCursor {}", firstCursor);
        log.info("getUsersPaginated - lastCursor {}", lastCursor);
        PageInfo pageInfo = new DefaultPageInfo(firstCursor, lastCursor, cursor != null, edges.size() >= number);

        return new DefaultConnection<>(edges, pageInfo);
    }

    private List<User> getUsersWithCursor(String cursor) {
        if (cursor == null) {
            return userService.getAllUsers();
        }
        return userService.getAllUsersAfter(Long.valueOf(cursor));
    }

    /**
     * @param userId
     * @param env    A DataFetchingEnvironment instance of passed to a DataFetcher as a execution context and its the place
     *               where you can find out information to help you resolve a data value given a graphql field input
     * @return User
     */
    //@PreAuthorize("hasAuthority('get:users')")
    public User getUserById(Long userId, DataFetchingEnvironment env) {
        // Selection fields set requested
        log.info("getUserById >>>>>> Selection set:");
        env.getSelectionSet().getFields().stream().map(SelectedField::getName).distinct().forEach(log::info);
        // Request variables
        log.info("getUserById >>>>> Variables:");
        env.getVariables().entrySet().stream().map(Object::toString).forEach(log::info);
        // Request arguments, filter arguments
        log.info("getUserById >>>>> Arguments:");
        env.getArguments().entrySet().stream().map(Object::toString).forEach(log::info);

        //        CustomGraphQLContext context = env.getContext();
        //        log.info("getUserById User Id: {}", context.getUserId()); // Este campo lo hemos definido en nuestro Custom Context

        return userService.getUserById(userId);
    }

    /**
     * @param userIds
     * @param env    A DataFetchingEnvironment instance of passed to a DataFetcher as a execution context and its the place
     *               where you can find out information to help you resolve a data value given a graphql field input
     * @return User
     */
    //@PreAuthorize("hasAuthority('get:users')")
    public  List<User> getUsersByIds(List<Long> userIds, DataFetchingEnvironment env) {
        log.info("getUsersByIds >>>>>> userIds: {}", userIds);
        // Selection fields set requested
        log.info("getUsersByIds >>>>>> Selection set:");
        env.getSelectionSet().getFields().stream().map(SelectedField::getName).distinct().forEach(log::info);
        // Request variables
        log.info("getUsersByIds >>>>> Variables:");
        env.getVariables().entrySet().stream().map(Object::toString).forEach(log::info);
        // Request arguments, filter arguments
        log.info("getUsersByIds >>>>> Arguments:");
        env.getArguments().entrySet().stream().map(Object::toString).forEach(log::info);


        return userService.getAllUsers().stream().filter(user -> userIds.contains(user.getId())).toList();
    }
}
