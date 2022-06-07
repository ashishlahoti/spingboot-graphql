package com.example.api.config.graphQL;

import graphql.schema.DataFetcher;
import graphql.schema.GraphQLFieldDefinition;
import graphql.schema.GraphQLFieldsContainer;
import graphql.schema.idl.SchemaDirectiveWiring;
import graphql.schema.idl.SchemaDirectiveWiringEnvironment;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
public class ValidateUserInputDirective implements SchemaDirectiveWiring {
    @Override
    public GraphQLFieldDefinition onField(SchemaDirectiveWiringEnvironment<GraphQLFieldDefinition> env) {

        GraphQLFieldDefinition field = env.getElement();
        GraphQLFieldsContainer parentType = env.getFieldsContainer();

        log.info(">>>>>>>>>  field: {}, parentType: {}", field.getName(), parentType.getName());
        log.info(">>>>>>>>>  field: {}, parentType: {}", field.getName(), parentType.getName());

        // build a data fetcher that transforms the given value to uppercase
        DataFetcher<?> originalFetcher = env.getCodeRegistry().getDataFetcher(parentType, field);

        DataFetcher<?> newDataFetcher = dataFetchingEnvironment -> {
            Map userInput = (Map) dataFetchingEnvironment.getArguments().get("input");
            if (userInput.get("email") != null &&  userInput.get("phone")!= null) {
                throw new IllegalArgumentException("You must specify either mail or phone, not both at the same time");
            } else {
                return originalFetcher.get(dataFetchingEnvironment);
            }
        };

        // now change the field definition to use the new uppercase data fetcher
        env.getCodeRegistry().dataFetcher(parentType, field, newDataFetcher);
        return field;
    }
}



