package com.example.game_analytics.repository.impl;

import com.example.game_analytics.model.UserGameAnalytics;
import com.github.benmanes.caffeine.cache.Cache;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserGameAnalyticsRepositoryTest {
    private UserGameAnalyticsRepository testInstance;
    private Cache<Integer, UserGameAnalytics> userCacheMock;

    @BeforeEach
    public void setUp() {
        userCacheMock = mock(Cache.class);
        testInstance = new UserGameAnalyticsRepository(userCacheMock);
        doNothing().when(userCacheMock).put(anyInt(), any());
    }

    @Test
    void testCreateHappyPath() {
        //given
        UserGameAnalytics analytics = new UserGameAnalytics();
        analytics.setId(1);

        //when
        testInstance.create(analytics);

        //then
        verify(userCacheMock).put(analytics.getId(), analytics);
    }

    @Test
    void testCreateUserAlreadyExists() {
        //given
        int userId = 1;
        UserGameAnalytics analytics = new UserGameAnalytics();
        analytics.setId(userId);
        when(userCacheMock.getIfPresent(userId)).thenReturn(analytics);

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> testInstance.create(analytics));

        //then
        verify(userCacheMock).getIfPresent(userId);
        assertEquals("User with ID [1] already exists.", exception.getMessage());
    }

    @Test
    void testGetByUserIdHappyPath() {
        //given
        int userId = 1;
        UserGameAnalytics analytics = new UserGameAnalytics();
        analytics.setId(userId);
        when(userCacheMock.getIfPresent(userId)).thenReturn(analytics);

        //when
        UserGameAnalytics result = testInstance.getByUserId(userId);

        //then
        verify(userCacheMock).getIfPresent(userId);
        assertEquals(analytics, result);
    }

    @Test
    void testUpdateHappyPath() {
        //given
        int userId = 1;
        UserGameAnalytics analytics = new UserGameAnalytics();
        analytics.setId(userId);
        when(userCacheMock.getIfPresent(userId)).thenReturn(analytics);

        //when
        testInstance.update(analytics);

        //then
        verify(userCacheMock).invalidate(userId);
        verify(userCacheMock).put(userId, analytics);
    }

    @Test
    void testUpdateUserDoesNotExist() {
        //given
        int userId = 1;
        UserGameAnalytics analytics = new UserGameAnalytics();
        analytics.setId(userId);
        when(userCacheMock.getIfPresent(userId)).thenReturn(null);

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> testInstance.update(analytics));

        //then
        verify(userCacheMock).getIfPresent(userId);
        assertEquals("User with ID [1] does not exist.", exception.getMessage());
    }
}
