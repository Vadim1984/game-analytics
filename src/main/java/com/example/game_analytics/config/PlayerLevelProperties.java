package com.example.game_analytics.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@ConfigurationProperties(prefix = "player.level")
public class PlayerLevelProperties {
    private Map<Integer, Integer> experience;

    public Map<Integer, Integer> getExperience() {
        return experience;
    }

    public void setExperience(Map<Integer, Integer> experience) {
        this.experience = experience;
    }
}
