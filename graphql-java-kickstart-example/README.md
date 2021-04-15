# GraphQL Java Kickstart Example
Examples to demo how to define a GraphQL schema and build GraphQL APIs using `com.graphql-java-kickstart` group libraries.

## GraphQL Java Kickstart
[GraphQL-Java-Kiskstart](https://github.com/graphql-java-kickstart) provides a wrapper on top of graphql-java and has following features:-
1. Provide comprehensive Spring boot configuration to customize GraphQL Java Server
2. Auto-detect schema files in `src/main/resources/*.*/*.graphqls` directory. This is where you write GraphQL schema, queries and mutation.
3. Concepts of Resolver. Implement `GraphQLQueryResolver`, `GraphQLMutationResolver` and `GraphQLResolver<T>` to specify how to fetch data for the queries, mutation and nested data respectively.
4. Easy integration with build tools such as GraphiQL, PlayGround and Voyager by adding runtime dependency. Provide comprehensive Spring Boot Configurations to customize these tools.
5. Easy to write integration test using `GraphQLTestTemplate` provided by test dependency.
6. Excellent tutorial series by [Philip Starritt](https://github.com/philip-jvm) which gives you quick start. 
