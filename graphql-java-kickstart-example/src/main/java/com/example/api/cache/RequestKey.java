package com.example.api.cache;

import lombok.Value;

import java.util.List;
import java.util.Map;

/**
 * key maintained by the Caffeine cache
 */
@Value
public class RequestKey {
    String userId;
    String query;
    String operationName;
    Map<String, Object> variables;
}
