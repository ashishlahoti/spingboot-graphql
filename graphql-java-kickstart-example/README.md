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

## Spring Boot Configuration
```yml
graphql:
  servlet:
    # Sets if GraphQL servlet should be created and exposed. If not specified defaults to "true".
    enabled: true
    # Sets the path where GraphQL servlet will be exposed. If not specified defaults to "/graphql"
    mapping: /graphql
    cors-enabled: true
    cors:
      allowed-origins: http://some.domain.com
      allowed-methods: GET, HEAD, POST
    # if you want to @ExceptionHandler annotation for custom GraphQLErrors
    exception-handlers-enabled: true
    context-setting: PER_REQUEST_WITH_INSTRUMENTATION
    # Sets if asynchronous operations are supported for GraphQL requests. If not specified defaults to true.
    async-mode-enabled: true
  tools:
    schema-location-pattern: "**/*.graphqls"
    # Enable or disable the introspection query. Disabling it puts your server in contravention of the GraphQL
    # specification and expectations of most clients, so use this option with caution
    introspection-enabled: true
  # Enable Scalars
  extended-scalars: BigDecimal, BigInteger, Byte, Char, Date, DateTime, JSON, Locale, Long, NegativeFloat, NegativeInt, NonNegativeFloat, NonNegativeInt, NonPositiveFloat, NonPositiveInt, Object, PositiveFloat, PositiveInt, Short, Time, Url
  # Enable tracking and metrics
  tracing-enabled: true
  actuator-metrics: true
```