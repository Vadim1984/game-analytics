package com.example.game_analytics.service.impl;

import com.example.game_analytics.config.PlayerLevelProperties;
import com.example.game_analytics.model.UserGameAnalytics;
import com.example.game_analytics.repository.IUserGameAnalyticsRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;

public class UserGameAnalyticsServiceTest {
    private UserGameAnalyticsService testInstance;
    private IUserGameAnalyticsRepository userGameAnalyticsRepository;
    private PlayerLevelProperties playerLevelProperties;

    @BeforeEach
    void setUp() {
        userGameAnalyticsRepository = mock(IUserGameAnalyticsRepository.class);
        playerLevelProperties = mock(PlayerLevelProperties.class);
        testInstance = spy(new UserGameAnalyticsService(userGameAnalyticsRepository, playerLevelProperties));
    }

    @ParameterizedTest
    @CsvSource({
            "1, 50, 50",
            "2, 101, 1"
    })
    void testAddUsersExperienceUser(int expectedLevel, int receivedExperience, int expectedExperience) {
        // given
        int userId = 1;
        UserGameAnalytics gameAnalytics = new UserGameAnalytics(userId, 0, 1);
        when(playerLevelProperties.getLevelToExperienceMap()).thenReturn(Map.of(2, 100, 3, 100));
        doNothing().when(userGameAnalyticsRepository).update(any());
        doReturn(gameAnalytics).when(testInstance).getById(userId);

        // when
        UserGameAnalytics userGameAnalyticsResult = testInstance.addUsersExperience(userId, receivedExperience);

        // then
        assertEquals(expectedLevel, userGameAnalyticsResult.getLevel());
        assertEquals(expectedExperience, userGameAnalyticsResult.getExp());
    }

    @Test
    void testGetByIdExistingUser() {
        // given
        int userId = 1;
        UserGameAnalytics existingAnalytics = new UserGameAnalytics(userId, 20, 2);
        when(userGameAnalyticsRepository.getByUserId(userId)).thenReturn(existingAnalytics);

        // when
        UserGameAnalytics result = testInstance.getById(userId);

        // then
        assertEquals(existingAnalytics, result);
    }

    @Test
    void testGetByIdNewUser() {
        // given
        int userId = 2;
        when(userGameAnalyticsRepository.getByUserId(userId)).thenReturn(null);
        UserGameAnalytics expectedUserAnalytics = new UserGameAnalytics(userId, 0, 1);

        // when
        UserGameAnalytics actualUserAnalytics = testInstance.getById(userId);

        // then
        assertEquals(expectedUserAnalytics, actualUserAnalytics);
    }
}
