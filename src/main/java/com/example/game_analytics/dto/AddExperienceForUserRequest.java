package com.example.game_analytics.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class AddExperienceForUserRequest {
    @Min(1)
    private Integer userId;
    @Min(1)
    private Integer experience;
}
