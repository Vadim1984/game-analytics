package com.example.game_analytics.repository;

import com.example.game_analytics.model.UserGameAnalytics;

public interface IUserGameAnalyticsRepository {
    UserGameAnalytics create(UserGameAnalytics analytics);
    UserGameAnalytics getByUserId(int userId);
    void update(UserGameAnalytics analytics);
}
