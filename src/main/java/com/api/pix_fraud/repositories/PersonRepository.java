package com.api.pix_fraud.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.pix_fraud.models.Person;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByIdAndActiveTrue(Long id);

    Optional<Person> findByEmailAndActiveTrue(String email);
}