package com.api.pix_fraud.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.pix_fraud.models.PersonCompany;

public interface PersonCompanyRepository extends JpaRepository<PersonCompany, Long> {
}
