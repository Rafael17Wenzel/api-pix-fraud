package com.api.pix_fraud.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.pix_fraud.models.Person;
import com.api.pix_fraud.models.dto.UserIdDTO;
import com.api.pix_fraud.services.BlacklistService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/blacklist")
public class BlacklistController {

    private final BlacklistService blacklistService;

    public BlacklistController(BlacklistService blacklistService) {
        this.blacklistService = blacklistService;
    }

    @PostMapping
    public ResponseEntity<Person> postUserInBlacklist(@RequestBody UserIdDTO userDTO) {
        Person blacklistUser = blacklistService.putUserIntoTheBlacklist(userDTO.getId());

        return ResponseEntity.ok(blacklistUser);
    }
    
    
    @GetMapping
    public ResponseEntity<List<Person>> getMethodName() {
        List<Person> users = blacklistService.getBlacklistUsers();

        return ResponseEntity.ok(users);
    }
    

}
