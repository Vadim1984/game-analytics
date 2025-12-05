package com.example.game_analytics.controller;

import com.example.game_analytics.dto.AddExperienceForUserRequest;
import com.example.game_analytics.dto.UserGameAnalyticsResponse;
import com.example.game_analytics.facade.UserGameAnalyticsFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final UserGameAnalyticsFacade userGameAnalyticsFacade;

    @Autowired
    public UserController(UserGameAnalyticsFacade userGameAnalyticsFacade) {
        this.userGameAnalyticsFacade = userGameAnalyticsFacade;
    }

    @PostMapping("/user/add-experience")
    public UserGameAnalyticsResponse getPlayerById(@RequestBody AddExperienceForUserRequest request) {
        return userGameAnalyticsFacade.addUsersExperience(request);
    }

    @PostMapping("/user/{id}")
    public UserGameAnalyticsResponse getPlayerById(@PathVariable("id") Integer id) {
        return userGameAnalyticsFacade.getById(id);
    }
}
