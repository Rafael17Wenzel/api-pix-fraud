package com.api.pix_fraud.models.dto;

import com.api.pix_fraud.models.PixCodeStatus;

public class PixCodeStatusDTO {
    
    private PixCodeStatus status;

    public PixCodeStatus getStatus() {
        return status;
    }

    public void setStatus(PixCodeStatus status) {
        this.status = status;
    }
}