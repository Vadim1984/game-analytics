package com.example.game_analytics.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;
import org.springframework.core.env.PropertySources;

import java.util.List;

import static com.example.game_analytics.config.PlayerLevelProperties.PLAYER_LEVEL_EXPERIENCE_PROPERTY_TOKEN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayerLevelPropertiesTest {
    private PlayerLevelProperties testInstance;
    private ConfigurableEnvironment environment;
    private PropertySources propertySources;
    private MutablePropertySources mutablePropertySources;
    private PropertySource<?> propertySource1;
    private PropertySource<?> propertySource2;

    @BeforeEach
    public void setUp() {
        mutablePropertySources = mock(MutablePropertySources.class);
        propertySource1 = mock(EnumerablePropertySource.class);
        propertySource2 = mock(EnumerablePropertySource.class);
        propertySources = mock(PropertySources.class);
        environment = mock(ConfigurableEnvironment.class);
        testInstance = new PlayerLevelProperties(environment);
        when(environment.getPropertySources()).thenReturn(mutablePropertySources);
        when(mutablePropertySources.iterator()).thenReturn(List.of(propertySource1, propertySource2).iterator());
    }

    @Test
    public void testLoadAndValidate() {
        // given
        String property1 = PLAYER_LEVEL_EXPERIENCE_PROPERTY_TOKEN + "1";
        String property1Value = "111";
        String property2 = PLAYER_LEVEL_EXPERIENCE_PROPERTY_TOKEN + "2";
        String property2Value = "";
        String property3 = PLAYER_LEVEL_EXPERIENCE_PROPERTY_TOKEN + "3";
        String property3Value = "";
        String property4 = PLAYER_LEVEL_EXPERIENCE_PROPERTY_TOKEN + "4";
        String property4Value = "444";
        String property5 = "some.other.property";
        when(((EnumerablePropertySource<?>) propertySource1).getPropertyNames())
                .thenReturn(new String[]{property1, property2, property3, property4, property5});
        when(((EnumerablePropertySource<?>) propertySource2).getPropertyNames())
                .thenReturn(new String[]{});
        when(environment.getProperty(property1)).thenReturn(property1Value);
        when(environment.getProperty(property2)).thenReturn(property2Value);
        when(environment.getProperty(property3)).thenReturn(property3Value);
        when(environment.getProperty(property4)).thenReturn(property4Value);

        // when
        testInstance.loadAndValidate();

        // then
        assertEquals(4, testInstance.getLevelToExperienceMap().size());
        assertEquals(111, testInstance.getLevelToExperienceMap().get(1));
        assertEquals(444, testInstance.getLevelToExperienceMap().get(2));
        assertEquals(444, testInstance.getLevelToExperienceMap().get(3));
        assertEquals(444, testInstance.getLevelToExperienceMap().get(4));
    }
}
