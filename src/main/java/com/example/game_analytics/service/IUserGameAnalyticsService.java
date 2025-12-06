package com.example.game_analytics.service;

import com.example.game_analytics.model.UserGameAnalytics;

public interface IUserGameAnalyticsService {
    UserGameAnalytics addUsersExperience(int userId, int receivedExperience);
    UserGameAnalytics getById(int userId);
}
