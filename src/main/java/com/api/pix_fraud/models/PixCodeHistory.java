package com.api.pix_fraud.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "\"pix_code_history\"")
public class PixCodeHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pix_code_id", nullable = false)
    private PixCode pixCode;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PixCodeStatus status;

    @Column(name = "checked_at")
    private LocalDateTime checkedAt = LocalDateTime.now();

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

    public PixCodeStatus getStatus() {
        return status;
    }

    public void setStatus(PixCodeStatus status) {
        this.status = status;
    }

    public LocalDateTime getCheckedAt() {
        return checkedAt;
    }

    public void setCheckedAt(LocalDateTime checkedAt) {
        this.checkedAt = checkedAt;
    }
}