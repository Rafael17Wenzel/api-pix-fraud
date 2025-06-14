package com.api.pix_fraud.controllers;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.pix_fraud.models.Person;
import com.api.pix_fraud.models.dto.request.PersonCompanyDTO;
import com.api.pix_fraud.models.dto.request.PersonIndividualDTO;
import com.api.pix_fraud.services.PersonService;

@RestController
@RequestMapping("/api/persons")
public class PersonController {

    @Autowired
    private PersonService personService;

    @PostMapping("/individual")
    public ResponseEntity<?> registerIndividual(@RequestBody PersonIndividualDTO dto) {
        try {
            Person created = personService.registerPersonIndividual(dto);
            return ResponseEntity.status(201).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @PostMapping("/company")
    public ResponseEntity<?> registerCompany(@RequestBody PersonCompanyDTO dto) {
        try {
            Person created = personService.registerPersonCompany(dto);
            return ResponseEntity.status(201).body(created);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(personService.findUserDetailsById(id));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email) {
        try {
            return ResponseEntity.ok(personService.findUserByEmail(email));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<?> deactivateUser(@PathVariable Long id) {
        try {
            personService.deactivateUser(id);
            return ResponseEntity.ok(Collections.singletonMap("message", "User deactivated successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Collections.singletonMap("error", e.getMessage()));
        }
    }
}