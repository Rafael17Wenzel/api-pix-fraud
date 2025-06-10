package com.api.pix_fraud.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.api.pix_fraud.models.UserReputation;
import com.api.pix_fraud.services.UserReputationService;

@RestController
@RequestMapping("/api/users/reputation")
public class UserReputationController {

    @Autowired
    private UserReputationService userReputationService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserReputation> getUserReputation(@PathVariable Long userId) {
        UserReputation reputation = userReputationService.getUserReputation(userId);
        return ResponseEntity.ok(reputation);
    }
}
