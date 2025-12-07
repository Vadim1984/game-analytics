package com.example.game_analytics.config;

import lombok.Setter;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

@Component
@ConfigurationProperties(prefix = "player.level")
public class PlayerLevelProperties implements InitializingBean {


    @Setter
    private Map<String, String> experience = new HashMap<>();

    private final Map<Integer, Integer> levelToExperienceMap = new TreeMap<>();

    @Override
    public void afterPropertiesSet() {
        validateLevelsAndExperience();
        fillMissingExperienceValues();
        experience.clear();
    }

    private void validateLevelsAndExperience() {
        for (Map.Entry<String, String> entry : experience.entrySet()) {
            Integer level = parseInt(entry.getKey());
            // level key must be valid integer
            if (level == null) {
                throw new IllegalArgumentException("Level key is empty. Please check configuration");
            }
            Integer exp = parseInt(entry.getValue());
            levelToExperienceMap.put(level, exp);
        }
    }

    /**
     * Parses a string to Integer, returns null if input is null/empty.
     * Throws exception if value is negative or not a number.
     */
    private static Integer parseInt(String valueStr) {
        try {
            if (valueStr == null || valueStr.isEmpty()) {
                return null;
            }

            int valueInt = Integer.parseInt(valueStr);
            validate(valueInt);
            return valueInt;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid value in configuration %s, value should be integer. Please check configuration"
                    .formatted(e.getMessage()));
        }
    }

    private static void validate(int valueInt) {
        if (valueInt < 0) {
            throw new IllegalArgumentException("property value [%s] should be non-negative. Please check configuration"
                    .formatted(valueInt));
        }
    }

    private void fillMissingExperienceValues() {
        Integer[] levels = levelToExperienceMap.keySet().toArray(new Integer[0]);

        for (int i = 0; i < levels.length; i++) {
            Integer level = levels[i];
            if (levelToExperienceMap.get(level) == null) {
                Integer nextExperience = findNextNonNullExperience(levels, i);
                if (nextExperience == null) {
                    throw new IllegalStateException("No valid experience value found for level [%s] or any higher level".formatted(level));
                }
                levelToExperienceMap.put(level, nextExperience);
            }
        }
    }

    private Integer findNextNonNullExperience(Integer[] levels, int currentIndex) {
        for (int j = currentIndex + 1; j < levels.length; j++) {
            Integer experience = this.levelToExperienceMap.get(levels[j]);
            if (experience != null) {
                return experience;
            }
        }
        return null;
    }

    public Map<Integer, Integer> getLevelToExperienceMap() {
        return new HashMap<>(levelToExperienceMap);
    }
}
