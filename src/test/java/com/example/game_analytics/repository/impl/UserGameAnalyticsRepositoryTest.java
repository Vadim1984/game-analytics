package com.example.game_analytics.repository.impl;

import com.example.game_analytics.model.UserGameAnalytics;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserGameAnalyticsRepositoryTest {
    private UserGameAnalyticsRepository testInstance;
    private ConcurrentMap<Integer, UserGameAnalytics> userCache;

    @BeforeEach
    public void setUp() {
        userCache = new ConcurrentHashMap<>();
        testInstance = new UserGameAnalyticsRepository(userCache);
    }

    @Test
    void testCreateHappyPath() {
        //given
        UserGameAnalytics analytics = new UserGameAnalytics();
        analytics.setId(1);

        //when
        testInstance.create(analytics);

        //then
        assertEquals(analytics, userCache.get(1));
    }

    @Test
    void testGetByUserIdHappyPath() {
        //given
        int userId = 1;
        UserGameAnalytics analytics = new UserGameAnalytics();
        analytics.setId(userId);
        userCache.put(userId, analytics);

        //when
        UserGameAnalytics result = testInstance.getByUserId(userId);

        //then
        assertEquals(analytics, result);
    }

    @Test
    void testUpdateHappyPath() {
        //given
        int userId = 1;
        UserGameAnalytics analytics = new UserGameAnalytics();
        analytics.setId(userId);
        userCache.put(userId, analytics);
        UserGameAnalytics updatedAnalytics = new UserGameAnalytics();
        updatedAnalytics.setId(userId);
        updatedAnalytics.setExp(100);

        //when
        testInstance.update(updatedAnalytics);

        //then
        assertEquals(updatedAnalytics, userCache.get(userId));
    }

    @Test
    void testUpdateUserDoesNotExist() {
        //given
        int userId = 1;
        UserGameAnalytics analytics = new UserGameAnalytics();
        analytics.setId(userId);

        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> testInstance.update(analytics));

        //then
        assertEquals("User with ID [1] does not exist.", exception.getMessage());
    }
}
