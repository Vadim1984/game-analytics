package com.example.game_analytics.repository.impl;

import com.example.game_analytics.model.UserGameAnalytics;
import com.example.game_analytics.repository.IUserGameAnalyticsRepository;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Service
public class UserGameAnalyticsRepository implements IUserGameAnalyticsRepository {
    private final ConcurrentMap<Integer, UserGameAnalytics> userCache;

    public UserGameAnalyticsRepository(ConcurrentHashMap<Integer, UserGameAnalytics> userCache) {
        this.userCache = userCache;
    }

    public UserGameAnalyticsRepository() {
        this.userCache = new ConcurrentHashMap<>();
    }

    @Override
    public UserGameAnalytics create(UserGameAnalytics analytics) {
        userCache.put(analytics.getId(), analytics);

        return analytics;
    }

    @Override
    public UserGameAnalytics getByUserId(int userId) {
        return userCache.get(userId);
    }

    @Override
    public synchronized void update(UserGameAnalytics analytics) {
        if (userCache.get(analytics.getId()) == null) {
            throw new IllegalArgumentException("User with ID [%s] does not exist.".formatted(analytics.getId()));
        }

        userCache.remove(analytics.getId());
        userCache.put(analytics.getId(), analytics);
    }

    private int generateId() {
        return (int) (Math.random() * Integer.MAX_VALUE);
    }
}
