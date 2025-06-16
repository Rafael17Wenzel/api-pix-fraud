package com.api.pix_fraud.models.dto.request;

import com.api.pix_fraud.models.FraudReportStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

public class FraudReportDTO {

    private Long id;

    @JsonProperty("pix_code_id")
    private Long pixCode;

    @JsonProperty("person_id")
    private Long personId;

    private String description;

    private FraudReportStatus status = FraudReportStatus.PENDING;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPixCodeId() {
        return pixCode;
    }

    public void getPixCodeId(Long pixCode) {
        this.pixCode = pixCode;
    }

    public Long getPersonId() {
        return personId;
    }

    public void setPerson(Long personId) {
        this.personId = personId;
    }

    public String getDescription() {
        return description;
    }

    public void setFraudType(String description) {
        this.description = description;
    }


    public FraudReportStatus getStatus() {
        return status;
    }

    public void setStatus(FraudReportStatus status) {
        this.status = status;
    }
}

