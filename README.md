## Spring Boot GraphQL
This provides examples to use GraphQL in a spring-boot based project.

## GraphQL
GraphQL is a query language to retrieve data from a server. It is an alternative to REST, SOAP or gRPC in some way. It provides more power to the consumer to customize the retrieval data.
## GraphQL Java
[GraphQL-Java](https://www.graphql-java.com/) is the Java (server) implementation for GraphQL. This provides a low level libraries to define GraphQL schema and how the actual data for a query is fetched. You have to write a lot of boilerplate code if you use graphql-java. 

Fortunately, We have two libraries available which provides a wrapper on top of graphql-java and saves use from writing boilerplate code. They are:-
1. GraphQL Java Kickstart
2. Netflix DGS

## GraphQL Java Kickstart
[GraphQL-Java-Kiskstart](https://github.com/graphql-java-kickstart) provides a wrapper on top of graphql-java and has following features:-
1. Provide comprehensive Spring boot configuration to customize GraphQL Java Server
2. Auto-detect schema files in `src/main/resources/*.*/*.graphqls` directory. This is where you write GraphQL schema, queries and mutation.
3. Concepts of Resolver. Implement `GraphQLQueryResolver`, `GraphQLMutationResolver` and `GraphQLResolver<T>` to specify how to fetch data for the queries, mutation and nested data respectively.   
4. Easy integration with build tools such as GraphiQL, PlayGround and Voyager by adding runtime dependency. Provide comprehensive Spring Boot Configurations to customize these tools.
5. Easy to write integration test using `GraphQLTestTemplate` provided by test dependency.
6. Excellent tutorial series by [Philip Starritt](https://github.com/philip-jvm) which gives you quick start. 

## Netflix DGS
[Netflix-DGS](https://netflix.github.io/dgs/) is developed by Netflix on top of graphql-java and recently made it public to use. It has following features:-
1. Do not provide Spring boot based configurations
2. Auto-detect schema files in `src/main/resources/schema/*.*/*.graphqls` directory. This is where you write GraphQL schema, queries and mutation.
3. Concepts of DataFetcher. Provide annotations `@DgsComponent` at class level and `@DgsQuery`, `@DgsMutation`, `@DgsData` at method level to specify how to fetch data for the queries, mutation and nested data respectively.
4. Provide integration with GraphiQL
5. Provide good support to write unit and integration test cases using `DgsQueryExecutor`
6. Follow [example](https://github.com/Netflix/dgs-examples-java) to quick start

