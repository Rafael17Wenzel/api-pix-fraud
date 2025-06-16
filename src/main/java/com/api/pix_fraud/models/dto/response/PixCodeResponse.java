package com.api.pix_fraud.models.dto.response;

import java.time.LocalDateTime;

import com.api.pix_fraud.models.PixCode;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PixCodeResponse {

    private Long id;

    private String code;

    @JsonProperty("person_id")
    private Long personId;

    private LocalDateTime createdAt;

    public PixCodeResponse() {
    }

    public PixCodeResponse(PixCode pixCode) {
        this.id = pixCode.getId();
        this.code = pixCode.getCode();
        this.personId = pixCode.getPerson().getId();
        this.createdAt = pixCode.getCreatedAt();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}