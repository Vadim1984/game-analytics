package com.example.game_analytics.facade;

import com.example.game_analytics.dto.AddExperienceForUserRequest;
import com.example.game_analytics.dto.UserGameAnalyticsResponse;

public interface IUserGameAnalyticsFacade {
    UserGameAnalyticsResponse addUsersExperience(AddExperienceForUserRequest request);
    UserGameAnalyticsResponse getById(int userId);
}
