package com.example.game_analytics.service;

import com.example.game_analytics.config.PlayerLevelProperties;
import com.example.game_analytics.model.UserGameAnalytics;
import com.example.game_analytics.repository.UserGameAnalyticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserGameAnalyticsService {

    private final UserGameAnalyticsRepository userGameAnalyticsRepository;
    private final PlayerLevelProperties playerLevelProperties;

    @Autowired
    public UserGameAnalyticsService(UserGameAnalyticsRepository userGameAnalyticsRepository,
                                    PlayerLevelProperties playerLevelProperties) {
        this.userGameAnalyticsRepository = userGameAnalyticsRepository;
        this.playerLevelProperties = playerLevelProperties;
    }

    public UserGameAnalytics addUsersExperience(int userId, int receivedExperience) {
        UserGameAnalytics userAnalytics = getById(userId);
        int currentTotalExperience = userAnalytics.getExp() + receivedExperience;
        int nextLevel = userAnalytics.getLevel() + 1;
        int experienceForLevelUp = playerLevelProperties.getLevelToExperienceMap().get(nextLevel);

        if (currentTotalExperience >= experienceForLevelUp) {
            userAnalytics.setLevel(nextLevel);
            userAnalytics.setExp(currentTotalExperience - experienceForLevelUp);
        } else {
            userAnalytics.setExp(currentTotalExperience);
        }

        userGameAnalyticsRepository.update(userAnalytics);

        return userAnalytics;
    }

    public UserGameAnalytics getById(int userId) {
        return Optional.ofNullable(userGameAnalyticsRepository.getByUserId(userId))
                .orElseGet(() -> create(userId));
    }


    private UserGameAnalytics create(int userId) {
        UserGameAnalytics newUserAnalytics = new UserGameAnalytics(userId, 0, 1);
        userGameAnalyticsRepository.create(newUserAnalytics);
        return newUserAnalytics;
    }
}
