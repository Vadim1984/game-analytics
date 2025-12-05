package com.example.game_analytics.facade;

import com.example.game_analytics.dto.AddExperienceForUserRequest;
import com.example.game_analytics.dto.UserGameAnalyticsResponse;
import com.example.game_analytics.model.UserGameAnalytics;
import com.example.game_analytics.service.UserGameAnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class UserGameAnalyticsFacade {

    private final Converter<UserGameAnalytics, UserGameAnalyticsResponse> modelToDtoConverter;
    private final UserGameAnalyticsService userGameAnalyticsService;

    @Autowired
    public UserGameAnalyticsFacade(Converter<UserGameAnalytics, UserGameAnalyticsResponse> modelToDtoConverter,
                                   UserGameAnalyticsService userGameAnalyticsService) {
        this.modelToDtoConverter = modelToDtoConverter;
        this.userGameAnalyticsService = userGameAnalyticsService;
    }

    public UserGameAnalyticsResponse addUsersExperience(AddExperienceForUserRequest request) {
        UserGameAnalytics updatedAnalytics = userGameAnalyticsService.addUsersExperience(request.getUserId(), request.getExperience());
        return modelToDtoConverter.convert(updatedAnalytics);
    }

    public UserGameAnalyticsResponse getById(int userId) {
        UserGameAnalytics userAnalytics = userGameAnalyticsService.getById(userId);
        return modelToDtoConverter.convert(userAnalytics);
    }
}
