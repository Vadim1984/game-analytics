package com.example.game_analytics.controller;

import com.example.game_analytics.dto.AddExperienceForUserRequest;
import com.example.game_analytics.dto.UserGameAnalyticsResponse;
import com.example.game_analytics.facade.IUserGameAnalyticsFacade;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserGameAnalyticsControllerTest {
    private UserGameAnalyticsController testInstance;
    private IUserGameAnalyticsFacade userGameAnalyticsFacadeMock;

    @BeforeEach
    void setUp() {
        userGameAnalyticsFacadeMock = mock(IUserGameAnalyticsFacade.class);
        testInstance = new UserGameAnalyticsController(userGameAnalyticsFacadeMock);
    }

    @Test
    void testAddUsersExperience() {
        // given
        AddExperienceForUserRequest request = new AddExperienceForUserRequest();
        request.setUserId(1);
        request.setExperience(50);
        UserGameAnalyticsResponse expectedResponse = new UserGameAnalyticsResponse();
        expectedResponse.setExperience(10);
        expectedResponse.setLevel(2);
        when(userGameAnalyticsFacadeMock.addUsersExperience(request)).thenReturn(expectedResponse);

        // when
        UserGameAnalyticsResponse actualGameAnalyticsResponse = testInstance.getPlayerById(request);

        // then
        assertEquals(expectedResponse, actualGameAnalyticsResponse);
    }

    @Test
    void testGetPlayerById() {
        // given
        int userId = 1;
        UserGameAnalyticsResponse expectedResponse = new UserGameAnalyticsResponse();
        expectedResponse.setExperience(20);
        expectedResponse.setLevel(3);
        when(userGameAnalyticsFacadeMock.getById(userId)).thenReturn(expectedResponse);

        // when
        UserGameAnalyticsResponse actualGameAnalyticsResponse = testInstance.getPlayerById(userId);

        // then
        assertEquals(expectedResponse, actualGameAnalyticsResponse);
    }
}
