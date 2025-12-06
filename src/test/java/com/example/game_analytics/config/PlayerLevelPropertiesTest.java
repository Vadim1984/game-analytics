package com.example.game_analytics.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlayerLevelPropertiesTest {
    private PlayerLevelProperties testInstance;

    @BeforeEach
    public void setUp() {
        testInstance = new PlayerLevelProperties();
    }

    @Test
    public void testLoadAndValidate() {
        // given
        Map<String, String> experience = new HashMap<>();
        experience.put("1", "111");
        experience.put("2", "");
        experience.put("3", "");
        experience.put("4", "444");
        testInstance.setExperience(experience);

        // when
        testInstance.afterPropertiesSet();

        // then
        assertEquals(4, testInstance.getLevelToExperienceMap().size());
        assertEquals(111, testInstance.getLevelToExperienceMap().get(1));
        assertEquals(444, testInstance.getLevelToExperienceMap().get(2));
        assertEquals(444, testInstance.getLevelToExperienceMap().get(3));
        assertEquals(444, testInstance.getLevelToExperienceMap().get(4));
    }

    @ParameterizedTest
    @CsvSource({
            ",111,Level key is empty. Please check configuration",
            "-1,111,property value [-1] should be non-negative. Please check configuration",
            "1,-111,property value [-111] should be non-negative. Please check configuration"
    })
    public void testValidationOfPropertyValues(String level, String exp, String expectedMessage) {
        Map<String, String> experience = new HashMap<>();
        experience.put(level, exp);
        testInstance.setExperience(experience);

        // when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> testInstance.afterPropertiesSet());

        // then
        assertEquals(expectedMessage, exception.getMessage());
    }
}
