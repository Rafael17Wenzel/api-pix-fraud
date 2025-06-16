package com.api.pix_fraud.controllers;

import java.util.Collections;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.pix_fraud.exceptions.ValidationException;
import com.api.pix_fraud.models.PixCode;
import com.api.pix_fraud.models.PixCodeHistory;
import com.api.pix_fraud.models.dto.request.PixCodeRequest;
import com.api.pix_fraud.services.PixCodeService;


@RestController
@RequestMapping("/api/pix_codes")
public class PixCodeController {

    private final PixCodeService pixCodeService;

    public PixCodeController(PixCodeService pixCodeService) {
        this.pixCodeService = pixCodeService;
    }

    @PostMapping
    public ResponseEntity<?> createPixCode(@RequestBody PixCodeRequest pixCodeRequest) {
        try {
            PixCode createdPixCode = pixCodeService.createPixCode(pixCodeRequest);

            return ResponseEntity.status(201).body(createdPixCode);
        } catch (ValidationException e) {
            return ResponseEntity.badRequest()
                .body(Collections.singletonMap("errors", e.getErrors()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getPixCode(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(pixCodeService.getPixCodeById(id));
        } catch (ValidationException e) {
            return ResponseEntity.badRequest()
                .body(Collections.singletonMap("errors", e.getErrors()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(Collections.singletonMap("error", e.getMessage()));
        }
    }
    

    @GetMapping("/person/{personId}")
    public ResponseEntity<?> getPixCodesByUser(@PathVariable Long personId) {
        try {
            return ResponseEntity.ok(pixCodeService.getPixCodesByUser(personId));
        } catch (ValidationException e) {
            return ResponseEntity.badRequest()
                .body(Collections.singletonMap("errors", e.getErrors()));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                .body(Collections.singletonMap("error", e.getMessage()));
        }
    }

    @GetMapping("/history/{pixCodeId}")
    public ResponseEntity<List<PixCodeHistory>> getPixCodeHistory(@PathVariable Long pixCodeId) {
        List<PixCodeHistory> history = pixCodeService.getPixCodeHistory(pixCodeId);
        if (history.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(history);
    }
}