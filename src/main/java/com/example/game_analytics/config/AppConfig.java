package com.example.game_analytics.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import java.util.TreeMap;

public class AppConfig {

    @Bean(name = "experienceToMaxLevelConfig")
    @ConfigurationProperties(prefix = "experience.to.max.level")
    public TreeMap<Integer, Integer> experienceToMaxLevelConfig() {
        return new TreeMap<>();
    }
}
