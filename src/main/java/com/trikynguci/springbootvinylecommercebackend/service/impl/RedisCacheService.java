package com.trikynguci.springbootvinylecommercebackend.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RedisCacheService {

    private final CacheManager cacheManager;

    public void refreshCache(String cacheName) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.clear();
        }
    }

}
