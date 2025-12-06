package com.example.game_analytics.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.TreeMap;

@Component
public class PlayerLevelProperties {

    public static final String PLAYER_LEVEL_EXPERIENCE_PROPERTY_TOKEN = "player.level.experience.";
    private final Environment environment;
    private final TreeMap<Integer, Integer> levelToExperienceMap = new TreeMap<>();

    @Autowired
    public PlayerLevelProperties(Environment environment) {
        this.environment = environment;
    }

    @PostConstruct
    public void loadAndValidate() {
        if (!(environment instanceof ConfigurableEnvironment configurableEnvironment)) {
            throw new IllegalStateException("Environment is not configurable");
        }

        // 1. Load all levels and their (possibly null) experience values
        for (PropertySource<?> propertySource : configurableEnvironment.getPropertySources()) {
            if (propertySource instanceof EnumerablePropertySource<?> enumerablePropertySource) {
                for (String propertyName : enumerablePropertySource.getPropertyNames()) {
                    if (propertyName.startsWith(PLAYER_LEVEL_EXPERIENCE_PROPERTY_TOKEN)) {
                        String levelStr = propertyName.substring(PLAYER_LEVEL_EXPERIENCE_PROPERTY_TOKEN.length());
                        Integer level = parseIntSafe(levelStr);
                        if (level != null) {
                            String experienceStr = environment.getProperty(propertyName);
                            Integer experience = parseIntSafe(experienceStr);
                            levelToExperienceMap.put(level, experience); // can be null
                        }
                    }
                }
            }
        }

        // 2. Fill missing values with the next bigger level's value
        fillMissingWithNextBigger();
    }

    public Map<Integer, Integer> getLevelToExperienceMap() {
        return levelToExperienceMap;
    }

    /**
     * Parses a string to Integer, returns null if input is null/empty.
     * Throws if value is negative or not a number.
     */
    private static Integer parseIntSafe(String valueStr) {
        try {
            if (valueStr == null || valueStr.isEmpty()) {
                return null;
            }

            int valueInt = Integer.parseInt(valueStr);
            validate(valueInt);
            return valueInt;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid value in configuration, details : [%s]. Please check configuration".formatted(e.getMessage()));
        }
    }

    private static void validate(int valueInt) {
        if (valueInt < 0) {
            throw new IllegalArgumentException("property value [%s] should be non-negative. Please check configuration"
                    .formatted(valueInt));
        }
    }

    /**
     * For each level with null experience, set it to the next higher non-null experience.
     */
    private void fillMissingWithNextBigger() {
        // Work with a copy of the keys to avoid ConcurrentModificationException
        Integer[] levels = levelToExperienceMap.keySet().toArray(new Integer[0]);
        for (int i = 0; i < levels.length; i++) {
            Integer level = levels[i];
            if (levelToExperienceMap.get(level) == null) {
                // Find next bigger level with non-null experience
                Integer nextExperience = null;
                for (int j = i + 1; j < levels.length; j++) {
                    Integer candidate = levelToExperienceMap.get(levels[j]);
                    if (candidate != null) {
                        nextExperience = candidate;
                        break;
                    }
                }
                if (nextExperience != null) {
                    levelToExperienceMap.put(level, nextExperience);
                } else {
                    throw new IllegalStateException("No non-null experience found for level " + level + " or any higher level. Please check configuration.");
                }
            }
        }
    }
}