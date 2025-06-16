package com.api.pix_fraud.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.pix_fraud.exceptions.ValidationException;
import com.api.pix_fraud.models.Person;
import com.api.pix_fraud.models.dto.UserIdDTO;
import com.api.pix_fraud.services.BlacklistService;

import java.util.Collections;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    public ResponseEntity<?> postUserInBlacklist(@RequestBody UserIdDTO userDTO) {
        try {
            Person blacklistUser = blacklistService.putUserIntoTheBlacklist(userDTO.getId());

        return ResponseEntity.ok(blacklistUser);
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
            List<Person> users = blacklistService.getBlacklistUsers();

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
    public ResponseEntity<?> removeUserFromBlacklist(@PathVariable Long id) {
        try {
            blacklistService.removeUserFromBlacklist(id);
            return ResponseEntity.ok(Collections.singletonMap("message", "Usuário removido da blacklist com sucesso."));
        } catch (ValidationException e) {
            return ResponseEntity.badRequest()
                .body(Collections.singletonMap("errors", e.getErrors()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(Collections.singletonMap("error", e.getMessage()));
        }
}
    

}
