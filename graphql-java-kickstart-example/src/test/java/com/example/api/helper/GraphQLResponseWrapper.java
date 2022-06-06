package com.example.api.helper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.graphql.spring.boot.test.GraphQLResponse;
import com.jayway.jsonpath.PathNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Slf4j
public class GraphQLResponseWrapper {
    private final ObjectMapper objectMapper;
    private final GraphQLResponse response;

    public List<?> getErrors() {
        return response.get("errors", List.class);
    }

    public <T> T get(String path, Class<T> clazz) {
        log.info("get path: {}, clazz: {}", path, clazz);
        try {
            String errors = response.get("errors", Object.class).toString();
            log.info("Errors: {}", errors);
        } catch (PathNotFoundException e) {
        }
        log.info("Body: {}", response.getRawResponse().getBody());
        return objectMapper.convertValue(response.get(path, Map.class), clazz);
    }
}
