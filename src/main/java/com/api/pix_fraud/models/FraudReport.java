package com.api.pix_fraud.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "fraud_report")
public class FraudReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "pix_code_id")
    private PixCode pixCode;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(optional = false)
    @JoinColumn(name = "fraud_type_id")
    private FraudType fraudType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FraudReportStatus status = FraudReportStatus.PENDING;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PixCode getPixCode() {
        return pixCode;
    }

    public void setPixCode(PixCode pixCode) {
        this.pixCode = pixCode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public FraudType getFraudType() {
        return fraudType;
    }

    public void setFraudType(FraudType fraudType) {
        this.fraudType = fraudType;
    }


    public FraudReportStatus getStatus() {
        return status;
    }

    public void setStatus(FraudReportStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
