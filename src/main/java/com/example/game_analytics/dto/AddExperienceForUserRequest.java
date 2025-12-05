package com.example.game_analytics.dto;

import lombok.Data;

@Data
public class AddExperienceForUserRequest {
    private int userId;
    private int experience;
}
