# Netflix DGS Example
Examples to demo how to define a GraphQL schema and build GraphQL APIs using `com.netflix.graphql.dgs` group libraries.

## Netflix DGS
[Netflix-DGS](https://netflix.github.io/dgs/) is developed by Netflix on top of graphql-java and recently made it public to use. It has following features:-
1. Do not provide Spring boot based configurations
2. Auto-detect schema files in `src/main/resources/schema/*.*/*.graphqls` directory. This is where you write GraphQL schema, queries and mutation.
3. Concepts of DataFetcher. Provide annotations `@DgsComponent` at class level and `@DgsQuery`, `@DgsMutation`, `@DgsData` at method level to specify how to fetch data for the queries, mutation and nested data respectively.
4. Provide integration with GraphiQL
5. Provide good support to write unit and integration test cases using `DgsQueryExecutor`
6. Follow [example](https://github.com/Netflix/dgs-examples-java) to quick start

