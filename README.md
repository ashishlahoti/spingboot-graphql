## Spring Boot GraphQL
This provides examples to use GraphQL in a spring-boot based project.

## GraphQL
GraphQL is a query language to retrieve data from a server. It is an alternative to REST, SOAP or gRPC in some way. It gives more power and flexibility to your API consumer to query only required data.
## GraphQL Java
[GraphQL-Java](https://www.graphql-java.com/) is the Java (server) implementation for GraphQL. This provides a low level libraries to define GraphQL schema and how the actual data for a query is fetched. You have to write a lot of boilerplate code if you use graphql-java. 

Fortunately, We have two libraries available which provides a wrapper on top of graphql-java and saves use from writing boilerplate code. They are:-
1. GraphQL Java Kickstart
2. Netflix DGS

## GraphQL Java Kickstart
[GraphQL-Java-Kiskstart](https://github.com/graphql-java-kickstart) provides a wrapper on top of graphql-java and has following features:-
1. Provide comprehensive Spring boot configuration to customize GraphQL Java Server
2. Auto-detect schema files in `src/main/resources/*.*/*.graphqls` directory. This is where you write GraphQL schema, queries and mutation.
3. **Resolvers**: Implement `GraphQLQueryResolver`, `GraphQLMutationResolver` and `GraphQLResolver<T>` to specify how to fetch data for the queries, mutation and nested data respectively.   
4. Easy integration with **build tools** such as GraphiQL, PlayGround and Voyager by adding runtime dependency. Provide comprehensive Spring Boot Configurations to customize these tools.
5. Easy to write integration **test** using `GraphQLTestTemplate` provided by test dependency.
6. Excellent tutorial series by [Philip Starritt](https://github.com/philip-jvm) which gives you quick start. 
7. Max Query Depth Limit
8. **Exception Handling** with
   1. ExceptionHandler
   2. GraphQLErrorHandler
9. Asynchronous Resolver
10. Mutations
11. File Upload
12. **DataFetchingEnvironment** object which allows it to know more about what is being fetched and what arguments have been provided.
13. **Selection set**: is the set of fields the user requested
14. graphql **extended scalars and custom scalars**
    1. DateTime and Date custom scalar types implemented by graphql java extended scalars
15. **Input Validation**: 
    1. JSR-303 bean validation annotations on our pojos
    2. Schema directive validation: In order to activate the directive validation with spring boot graphql, you must first create a ValidationSchemaWiring bean.
16. **Listeners**: allows you to add a Servlet Listener for listening to the GraphQL request.
17. **Pagination** (Edges, Nodes, Cursor)  cursor-based pagination is the most powerful pagination pattern so-far
18. **Custom Context**: you have the ability to create a custom object once at the very start of the query/mutation, and this object will be available to all mutations and queries via the DataFetchingEnvironmen
19. **DataLoader (N+1 problem)**: By default in GraphQL, a query resolver will resolve a field's nodes sequentially. In most cases you would want to group together Field X values and batch them to the backing API. This leads to massive performance gains in reduction of network traffic and optimal database queries on the datasource.
20. **Instrumentation**: allows you to inject code that can observe the execution of a query and also change the runtime behaviour.
21. **Request Tracing**: identify slow resolvers and bottlenecks. One method is to add request tracing to your resolvers/datafetchers, dataloaders and fields. We can enable the **TracingInstrumentation** bean
22. **Correlation ID** (Thread propagation): In a multi-threaded graphql server it is imperative to propagate a request correlation id to all threads invoked. We then customize the logback console appender to print the %X{correlation_id} with every log line. 
23. **DataLoader Key Context**: N+1 problem second approach
24. **Spring Security Authorization**: Configure our Spring Boot GraphQL server with Spring Security pre authorization.
25. **MDC Correlation ID**: GraphQL version 12.0.0+ executes asynchronously by default. This introduced an additional thread-pool (created inside GraphQLWebSecurityAutoConfiguration or GraphQLWebAutoConfiguration) that will be execute the resolver threadsThe Correlation ID is propagated from the Tomcat NIO Thread to the new thread-pool via a graphql.kickstart.servlet.AsyncTaskDecorator. One class MdcContextTaskDecorator implements both interfaces, to keep things simple.
26. **Query Caching Server Side**: cache GraphQL queries on the server side

## Netflix DGS
[Netflix-DGS](https://netflix.github.io/dgs/) is developed by Netflix on top of graphql-java and recently made it public to use. It has following features:-
1. Do not provide Spring boot based configurations
2. Auto-detect schema files in `src/main/resources/schema/*.*/*.graphqls` directory. This is where you write GraphQL schema, queries and mutation.
3. Concepts of DataFetcher. Provide annotations `@DgsComponent` at class level and `@DgsQuery`, `@DgsMutation`, `@DgsData` at method level to specify how to fetch data for the queries, mutation and nested data respectively.
4. Provide integration with GraphiQL
5. Provide good support to write unit and integration test cases using `DgsQueryExecutor`
6. Follow [example](https://github.com/Netflix/dgs-examples-java) to quick start

