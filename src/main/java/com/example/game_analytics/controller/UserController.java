package com.example.game_analytics.controller;

import com.example.game_analytics.dto.AddExperienceForUserRequest;
import com.example.game_analytics.dto.UserGameAnalyticsResponse;
import com.example.game_analytics.facade.IUserGameAnalyticsFacade;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    private final IUserGameAnalyticsFacade userGameAnalyticsFacade;

    @Autowired
    public UserController(IUserGameAnalyticsFacade userGameAnalyticsFacade) {
        this.userGameAnalyticsFacade = userGameAnalyticsFacade;
    }

    @PostMapping(path = "/user/add-experience", consumes = "application/json", produces = "application/json")
    public UserGameAnalyticsResponse getPlayerById(@Valid @RequestBody AddExperienceForUserRequest request) {
        return userGameAnalyticsFacade.addUsersExperience(request);
    }

    @PostMapping(path = "/user/{id}", produces = "application/json")
    public UserGameAnalyticsResponse getPlayerById(@PathVariable("id") Integer id) {
        return userGameAnalyticsFacade.getById(id);
    }
}
