package com.api.pix_fraud.models.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PixCodeRequest {

    private String code;

    @JsonProperty("person_id")
    private Long personId;



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
}
