package com.example.api.config.cache;

import com.example.api.cache.RequestKey;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import graphql.kickstart.servlet.cache.CachedResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Slf4j
@Configuration
public class CacheConfig {

    /**
     * A builder of Cache, LoadingCache, AsyncCache, and AsyncLoadingCache instances having a combination of the following
     * features:
     * - automatic loading of entries into the cache, optionally asynchronously
     * - size-based eviction when a maximum is exceeded based on frequency and recency
     * - time-based expiration of entries, measured since last access or last write
     * - asynchronously refresh when the first stale request for an entry occurs
     * - keys automatically wrapped in weak references
     * - values automatically wrapped in weak or soft references
     * - writes propagated to an external resource
     * - notification of evicted (or otherwise removed) entries
     * - accumulation of cache access statistics
     *
     * @return Cache<RequestKey, CachedResponse>
     */
    @Bean
    public Cache<RequestKey, CachedResponse> responseCache() {
        return Caffeine.newBuilder()
                .expireAfterWrite(Duration.ofMinutes(1L))   // Specifies that each entry should be automatically removed
                                                            // from the cache once a fixed duration has elapsed
                .maximumSize(100L) // Specifies the maximum number of entries the cache may contain.
                .removalListener((key, value, cause) ->
                        log.info("###### Key {} with value {} was removed from the response cache. Cause {} ######", key, value, cause)
                )   // Specifies a listener instance that caches should notify each time an entry is removed for any reason.
                .build();
    }

}
