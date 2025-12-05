package com.example.game_analytics.converter;

import com.example.game_analytics.dto.UserGameAnalyticsResponse;
import com.example.game_analytics.model.UserGameAnalytics;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Service;

@Service
public class ModelToDtoConverter implements Converter<UserGameAnalytics, UserGameAnalyticsResponse> {

    public UserGameAnalyticsResponse convert(UserGameAnalytics userGameAnalytics) {
        UserGameAnalyticsResponse response = new UserGameAnalyticsResponse();
        response.setExperience(userGameAnalytics.getExp());
        response.setLevel(userGameAnalytics.getLevel());

        return response;
    }
}
