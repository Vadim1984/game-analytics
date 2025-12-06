package com.example.game_analytics.facade.impl;

import com.example.game_analytics.dto.AddExperienceForUserRequest;
import com.example.game_analytics.dto.UserGameAnalyticsResponse;
import com.example.game_analytics.facade.IUserGameAnalyticsFacade;
import com.example.game_analytics.model.UserGameAnalytics;
import com.example.game_analytics.service.IUserGameAnalyticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class UserGameAnalyticsFacade implements IUserGameAnalyticsFacade {

    private final Converter<UserGameAnalytics, UserGameAnalyticsResponse> modelToDtoConverter;
    private final IUserGameAnalyticsService userGameAnalyticsService;

    @Autowired
    public UserGameAnalyticsFacade(Converter<UserGameAnalytics, UserGameAnalyticsResponse> modelToDtoConverter,
                                   IUserGameAnalyticsService userGameAnalyticsService) {
        this.modelToDtoConverter = modelToDtoConverter;
        this.userGameAnalyticsService = userGameAnalyticsService;
    }

    @Override
    public UserGameAnalyticsResponse addUsersExperience(AddExperienceForUserRequest request) {
        UserGameAnalytics updatedAnalytics = userGameAnalyticsService.addUsersExperience(request.getUserId(), request.getExperience());
        return modelToDtoConverter.convert(updatedAnalytics);
    }

    @Override
    public UserGameAnalyticsResponse getById(int userId) {
        UserGameAnalytics userAnalytics = userGameAnalyticsService.getById(userId);
        return modelToDtoConverter.convert(userAnalytics);
    }
}
