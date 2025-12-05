package com.example.game_analytics.config;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class PlayerLevelProperties {

    public static final String PLAYER_LEVEL_EXPERIENCE_PROPERTY_TOKEN = "player.level.experience.";
    private final Environment environment;
    private final Map<Integer, Integer> levelToexperienceMap = new HashMap<>();

    @Autowired
    public PlayerLevelProperties(Environment environment) {
        this.environment = environment;
    }

    @PostConstruct
    public void loadAndValidate() {
        if (!(environment instanceof ConfigurableEnvironment configurableEnvironment)) {
            throw new IllegalStateException("Environment is not configurable");
        }

        for (PropertySource<?> propertySource : configurableEnvironment.getPropertySources()) {
            if (propertySource instanceof EnumerablePropertySource) {
                for (String propertyName : ((EnumerablePropertySource<?>) propertySource).getPropertyNames()) {
                    if (propertyName.startsWith(PLAYER_LEVEL_EXPERIENCE_PROPERTY_TOKEN)) {
                        String levelStr = propertyName.substring(PLAYER_LEVEL_EXPERIENCE_PROPERTY_TOKEN.length());
                        String ExperienceStr = environment.getProperty(propertyName);
                        int level = parceInt(levelStr);
                        if (ExperienceStr != null && !ExperienceStr.isEmpty()) {
                            int experience = parceInt(ExperienceStr);
                            levelToexperienceMap.put(level, experience);
                        } else {
                            levelToexperienceMap.put(level, null);
                        }
                    }
                }
            }
        }
    }

    public Map<Integer, Integer> getLevelToExperienceMap() {
        return levelToexperienceMap;
    }

    private static int parceInt(String valueStr) {
        try {
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
}
