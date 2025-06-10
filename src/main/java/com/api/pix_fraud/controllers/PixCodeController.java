package com.api.pix_fraud.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.pix_fraud.models.PixCode;
import com.api.pix_fraud.models.PixCodeHistory;
import com.api.pix_fraud.models.User;
import com.api.pix_fraud.models.dto.PixCodeStatusDTO;
import com.api.pix_fraud.services.PixCodeService;

@RestController
@RequestMapping("/api/pix_codes")
public class PixCodeController {

    private final PixCodeService pixCodeService;

    public PixCodeController(PixCodeService pixCodeService) {
        this.pixCodeService = pixCodeService;
    }

    @PostMapping
    public ResponseEntity<PixCode> createPixCode(@RequestBody PixCode pixCode) {
        PixCode createdPixCode = pixCodeService.createPixCode(pixCode);
        return ResponseEntity.status(201).body(createdPixCode);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<PixCode>> getPixCodesByUser(@PathVariable Long userId) {
        User user = new User();
        user.setId(userId);
        List<PixCode> pixCodes = pixCodeService.getPixCodesByUser(user);
        if (pixCodes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pixCodes);
    }

    @PutMapping("/{pixCodeId}")
    public ResponseEntity<PixCode> updateStatus(@PathVariable Long pixCodeId, @RequestBody PixCodeStatusDTO status) {
        PixCode updatedPixCode = pixCodeService.updateStatus(pixCodeId, status);
        return ResponseEntity.ok(updatedPixCode);
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