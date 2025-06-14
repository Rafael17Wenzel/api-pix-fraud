package com.api.pix_fraud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.pix_fraud.models.PersonIndividual;

public interface PersonIndividualRepository extends JpaRepository<PersonIndividual, Long> {
}