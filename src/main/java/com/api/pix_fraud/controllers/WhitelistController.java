package com.api.pix_fraud.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.pix_fraud.exceptions.ValidationException;
import com.api.pix_fraud.models.Person;
import com.api.pix_fraud.models.dto.UserIdDTO;
import com.api.pix_fraud.services.WhitelistService;

import java.util.Collections;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<?> postUserInwhitelist(@RequestBody UserIdDTO userDTO) {
        try {
            Person whitelistUser = whitelistService.putUserIntoTheWhitelist(userDTO.getId());

            return ResponseEntity.ok(whitelistUser);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest()
                .body(Collections.singletonMap("errors", e.getErrors()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(Collections.singletonMap("error", e.getMessage()));
        }
        
    }
    
    
    @GetMapping
    public ResponseEntity<?> getMethodName() {
        try {
            List<Person> users = whitelistService.getWhitelistUsers();

            return ResponseEntity.ok(users);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest()
                .body(Collections.singletonMap("errors", e.getErrors()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> removeUserFromWhitelist(@PathVariable Long id) {
        try {
            whitelistService.removeUserFromWhitelist(id);
            return ResponseEntity.ok(Collections.singletonMap("message", "Usuário removido da whitelist com sucesso."));
        } catch (ValidationException e) {
            return ResponseEntity.badRequest()
                .body(Collections.singletonMap("errors", e.getErrors()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(Collections.singletonMap("error", e.getMessage()));
        }
    }
}
