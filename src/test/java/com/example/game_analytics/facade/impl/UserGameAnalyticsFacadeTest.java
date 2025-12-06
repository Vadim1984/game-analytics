package com.example.game_analytics.facade.impl;

import com.example.game_analytics.dto.AddExperienceForUserRequest;
import com.example.game_analytics.dto.UserGameAnalyticsResponse;
import com.example.game_analytics.model.UserGameAnalytics;
import com.example.game_analytics.service.IUserGameAnalyticsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.convert.converter.Converter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserGameAnalyticsFacadeTest {
    private UserGameAnalyticsFacade testInstance;
    private Converter<UserGameAnalytics, UserGameAnalyticsResponse> modelToDtoConverter;
    private IUserGameAnalyticsService userGameAnalyticsService;

    @BeforeEach
    void setUp() {
        modelToDtoConverter = mock(Converter.class);
        userGameAnalyticsService = mock(IUserGameAnalyticsService.class);
        testInstance = new UserGameAnalyticsFacade(modelToDtoConverter, userGameAnalyticsService);
    }

    @Test
    void testAddUsersExperience() {
        // given
        AddExperienceForUserRequest request = new AddExperienceForUserRequest();
        request.setUserId(1);
        request.setExperience(100);
        UserGameAnalytics updatedAnalytics = new UserGameAnalytics(1, 50, 2);
        when(userGameAnalyticsService.addUsersExperience(1, 100)).thenReturn(updatedAnalytics);
        UserGameAnalyticsResponse expectedUserGameAnalyticsResponse = new UserGameAnalyticsResponse();
        expectedUserGameAnalyticsResponse.setLevel(updatedAnalytics.getLevel());
        expectedUserGameAnalyticsResponse.setExperience(updatedAnalytics.getExp());
        when(modelToDtoConverter.convert(updatedAnalytics)).thenReturn(expectedUserGameAnalyticsResponse);

        // when
        UserGameAnalyticsResponse actualGameAnalyticsResponse = testInstance.addUsersExperience(request);

        // then
        assertEquals(expectedUserGameAnalyticsResponse, actualGameAnalyticsResponse);
    }

    @Test
    void testGetById() {
        // given
        int userId = 1;
        UserGameAnalytics userAnalytics = new UserGameAnalytics(userId, 20, 2);
        when(userGameAnalyticsService.getById(userId)).thenReturn(userAnalytics);
        UserGameAnalyticsResponse expectedUserGameAnalyticsResponse = new UserGameAnalyticsResponse();
        expectedUserGameAnalyticsResponse.setLevel(userAnalytics.getLevel());
        expectedUserGameAnalyticsResponse.setExperience(userAnalytics.getExp());
        when(modelToDtoConverter.convert(userAnalytics)).thenReturn(expectedUserGameAnalyticsResponse);

        // when
        UserGameAnalyticsResponse actualGameAnalyticsResponse = testInstance.getById(userId);

        // then
        assertEquals(expectedUserGameAnalyticsResponse, actualGameAnalyticsResponse);
    }
}
