package com.api.pix_fraud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.pix_fraud.models.PixCodeHistory;

import java.util.List;

public interface PixCodeHistoryRepository extends JpaRepository<PixCodeHistory, Long> {

    List<PixCodeHistory> findByPixCodeId(Long pixCodeId);
}