package com.example.game_analytics.validator;

import com.example.game_analytics.config.PlayerLevelProperties;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PlayerLevelValidator {
    private final PlayerLevelProperties properties;

    public PlayerLevelValidator(PlayerLevelProperties properties) {
        this.properties = properties;
    }

    @PostConstruct
    public void validate() {
        Map<Integer, Integer> experienceMap = properties.getExperience();
        if (experienceMap == null || experienceMap.isEmpty()) {
            throw new IllegalStateException("Experience map is not configured!");
        }
    }
}
