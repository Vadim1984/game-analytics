package com.example.game_analytics.repository.impl;

import com.example.game_analytics.model.UserGameAnalytics;
import com.example.game_analytics.repository.IUserGameAnalyticsRepository;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class UserGameAnalyticsRepository implements IUserGameAnalyticsRepository {
    private final ConcurrentMap<Integer, UserGameAnalytics> userCache;

    public UserGameAnalyticsRepository(ConcurrentMap<Integer, UserGameAnalytics> userCache) {
        this.userCache = userCache;
    }

    public UserGameAnalyticsRepository() {
        this.userCache = new ConcurrentHashMap<>();
    }

    @Override
    public UserGameAnalytics create(UserGameAnalytics analytics) {
        return userCache.put(analytics.getId(), analytics);
    }

    @Override
    public UserGameAnalytics getByUserId(int userId) {
        return userCache.get(userId);
    }

    @Override
    public void update(UserGameAnalytics analytics) {
        userCache.compute(analytics.getId(), (key, existing) -> {
            if (existing == null) {
                throw new IllegalArgumentException("User with ID [%s] does not exist.".formatted(key));
            }
            return analytics;
        });
    }
}
