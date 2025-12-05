package com.example.game_analytics.service;

import com.example.game_analytics.model.UserGameAnalytics;
import com.example.game_analytics.repository.UserGameAnalyticsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.TreeMap;

@Service
public class UserGameAnalyticsService {

    private final UserGameAnalyticsRepository userGameAnalyticsRepository;
    private final TreeMap<Integer, Integer> experienceToMaxLevelConfig;

    @Autowired
    public UserGameAnalyticsService(UserGameAnalyticsRepository userGameAnalyticsRepository,
                                    TreeMap<Integer, Integer> experienceToMaxLevelConfig) {
        this.userGameAnalyticsRepository = userGameAnalyticsRepository;
        this.experienceToMaxLevelConfig = experienceToMaxLevelConfig;
    }

    public UserGameAnalytics addUsersExperience(int userId, int experience) {
        UserGameAnalytics userAnalytics = getById(userId);

        int level = experienceToMaxLevelConfig.floorEntry(experience).getValue();
        userAnalytics.setExp(userAnalytics.getExp() + experience);
        userAnalytics.setLevel(level);
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
