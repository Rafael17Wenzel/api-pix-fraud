package com.api.pix_fraud.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.pix_fraud.models.Person;
import com.api.pix_fraud.models.dto.UserIdDTO;
import com.api.pix_fraud.services.WhitelistService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/whitelist")
public class WhitelistController {

    private final WhitelistService whitelistService;

    public WhitelistController(WhitelistService whitelistService) {
        this.whitelistService = whitelistService;
    }

    @PostMapping
    public ResponseEntity<Person> postUserInwhitelist(@RequestBody UserIdDTO userDTO) {
        Person whitelistUser = whitelistService.putUserIntoTheWhitelist(userDTO.getId());

        return ResponseEntity.ok(whitelistUser);
    }
    
    
    @GetMapping
    public ResponseEntity<List<Person>> getMethodName() {
        List<Person> users = whitelistService.getWhitelistUsers();

        return ResponseEntity.ok(users);
    }
    

}
