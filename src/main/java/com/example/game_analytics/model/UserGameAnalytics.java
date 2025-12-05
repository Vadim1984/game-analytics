package com.example.game_analytics.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserGameAnalytics {
    private int id;
    private int exp;
    private int level;
}
