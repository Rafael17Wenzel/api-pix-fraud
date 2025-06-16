package com.api.pix_fraud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.pix_fraud.models.PixCode;
import com.api.pix_fraud.models.Person;

import java.util.List;
import java.util.Optional;

public interface PixCodeRepository extends JpaRepository<PixCode, Long> {

    Optional<PixCode> findByCodeAndPersonActiveTrue(String code);
    
    List<PixCode> findByPerson(Person user);

    Optional<PixCode> findByIdAndPerson(Long id, Person user);
}