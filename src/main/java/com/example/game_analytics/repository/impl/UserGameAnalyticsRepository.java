package com.example.game_analytics.repository.impl;

import com.example.game_analytics.model.UserGameAnalytics;
import com.example.game_analytics.repository.IUserGameAnalyticsRepository;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class UserGameAnalyticsRepository implements IUserGameAnalyticsRepository {
    private final Cache<Integer, UserGameAnalytics> userCache;

    public UserGameAnalyticsRepository() {
        this.userCache = Caffeine.newBuilder()
                .expireAfterWrite(1, TimeUnit.MINUTES)
                .maximumSize(100)
                .build();
    }

    @Override
    public UserGameAnalytics create(UserGameAnalytics analytics) {
        if (userCache.getIfPresent(analytics.getId()) != null) {
            throw new IllegalArgumentException("User with ID [%s] already exists.".formatted(analytics.getId()));
        }
        userCache.put(analytics.getId(), analytics);

        return analytics;
    }

    @Override
    public UserGameAnalytics getByUserId(int userId) {
        return userCache.getIfPresent(userId);
    }

    @Override
    public void update(UserGameAnalytics analytics) {
        if( userCache.getIfPresent(analytics.getId()) == null) {
            throw new IllegalArgumentException("User with ID [%s] does not exist.".formatted(analytics.getId()));
        }

        userCache.invalidate(analytics.getId());
        userCache.put(analytics.getId(), analytics);
    }

    private int generateId() {
        return (int) (Math.random() * Integer.MAX_VALUE);
    }
}
