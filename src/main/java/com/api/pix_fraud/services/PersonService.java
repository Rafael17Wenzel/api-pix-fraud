package com.api.pix_fraud.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import com.api.pix_fraud.exceptions.ValidationException;
import com.api.pix_fraud.models.Person;
import com.api.pix_fraud.models.PersonCompany;
import com.api.pix_fraud.models.PersonIndividual;
import com.api.pix_fraud.models.dto.request.PersonCompanyDTO;
import com.api.pix_fraud.models.dto.request.PersonIndividualDTO;
import com.api.pix_fraud.models.dto.response.PersonCompanyResponse;
import com.api.pix_fraud.models.dto.response.PersonIndividualResponse;
import com.api.pix_fraud.repositories.PersonCompanyRepository;
import com.api.pix_fraud.repositories.PersonIndividualRepository;
import com.api.pix_fraud.repositories.PersonRepository;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    private PersonIndividualRepository individualRepository;

    @Autowired
    private PersonCompanyRepository companyRepository;

    @Autowired
    private AuditService auditService;

    @Transactional
    public Person registerPersonIndividual(PersonIndividualDTO dto) {
        if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
            throw new RuntimeException("Email é obrigatório.");
        }

        Optional<Person> existing = personRepository.findByEmailAndActiveTrue(dto.getEmail());
        if (existing.isPresent()) {
            throw new RuntimeException("Usuário com e-mail já existe.");
        }

        List<String> errors = new ArrayList<>();

        if (dto.getName() == null || dto.getName().trim().isEmpty()) {
            errors.add("Nome é obrigatório.");
        }

        if (dto.getCpf() == null || dto.getCpf().trim().isEmpty()) {
            errors.add("CPF é obrigatório.");
        }

        if (dto.getBirthDate() == null) {
            errors.add("Data de nascimento é obrigatória.");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        if (personRepository.findByEmailAndActiveTrue(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Usuário com e-mail já existe.");
        }

        Person person = new Person();
        person.setEmail(dto.getEmail());
        person.setPhone(dto.getPhone());
        person.setActive(dto.isActive());

        Person savedPerson = personRepository.save(person);

        PersonIndividual individual = new PersonIndividual();
        individual.setPerson(savedPerson);
        individual.setName(dto.getName());
        individual.setCpf(dto.getCpf());
        individual.setBirthDate(dto.getBirthDate());

        individualRepository.save(individual);

        auditService.log(savedPerson, "Registro de pessoa física",
                "Pessoa física registrada com id: " + savedPerson.getId());

        return savedPerson;
    }

    @Transactional
    public Person registerPersonCompany(PersonCompanyDTO dto) {
        if (dto.getEmail() == null || dto.getEmail().trim().isEmpty()) {
            throw new RuntimeException("Email é obrigatório.");
        }

        Optional<Person> existing = personRepository.findByEmailAndActiveTrue(dto.getEmail());
        if (existing.isPresent()) {
            throw new RuntimeException("Usuário com e-mail já existe.");
        }

        List<String> errors = new ArrayList<>();

        if (dto.getCnpj() == null || dto.getCnpj().trim().isEmpty()) {
            errors.add("CNPJ é obrigatório.");
        }

        if (dto.getCorporateName() == null || dto.getCorporateName().trim().isEmpty()) {
            errors.add("Razão social (corporateName) é obrigatória.");
        }

        if (dto.getCompanyName() == null || dto.getCompanyName().trim().isEmpty()) {
            errors.add("Nome fantasia (companyName) é obrigatório.");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException(errors);
        }

        if (personRepository.findByEmailAndActiveTrue(dto.getEmail()).isPresent()) {
            throw new RuntimeException("Usuário com e-mail já existe.");
        }

        Person person = new Person();
        person.setEmail(dto.getEmail());
        person.setPhone(dto.getPhone());
        person.setActive(dto.isActive());

        Person savedPerson = personRepository.save(person);

        PersonCompany company = new PersonCompany();
        company.setPerson(savedPerson);
        company.setCnpj(dto.getCnpj());
        company.setCorporateName(dto.getCorporateName());
        company.setCompanyName(dto.getCompanyName());

        companyRepository.save(company);

        auditService.log(savedPerson, "Registro de pessoa jurídica",
                "Pessoa jurídica registrada com id: " + savedPerson.getId());

        return savedPerson;
    }

    public Object findUserDetailsById(Long id) {
        Person person = personRepository.findByIdAndActiveTrue(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));

        Optional<PersonIndividual> individual = individualRepository.findById(id);
        if (individual.isPresent()) {
            PersonIndividual i = individual.get();

            PersonIndividualResponse dto = new PersonIndividualResponse();
            dto.setId(person.getId());
            dto.setEmail(person.getEmail());
            dto.setPhone(person.getPhone());
            dto.setActive(person.isActive());

            dto.setName(i.getName());
            dto.setCpf(i.getCpf());
            dto.setBirthDate(i.getBirthDate());

            auditService.log(person, "Consulta de pessoa física", "ID: " + id);
            return dto;
        }

        Optional<PersonCompany> company = companyRepository.findById(id);
        if (company.isPresent()) {
            PersonCompany c = company.get();

            PersonCompanyResponse dto = new PersonCompanyResponse();
            dto.setId(person.getId());
            dto.setEmail(person.getEmail());
            dto.setPhone(person.getPhone());
            dto.setActive(person.isActive());

            dto.setCnpj(c.getCnpj());
            dto.setCorporateName(c.getCorporateName());
            dto.setCompanyName(c.getCompanyName());

            auditService.log(person, "Consulta de pessoa jurídica", "ID: " + id);
            return dto;
        }

        throw new RuntimeException("Usuário sem tipo associado (física ou jurídica)");
    }

    public Person findUserByEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new RuntimeException("Email é obrigatório.");
        }

        Person person = personRepository.findByEmailAndActiveTrue(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado com esse e-mail."));

        auditService.log(person, "Consulta de usuário por e-mail", "E-mail consultado: " + email);
        return person;
    }

    public void deactivateUser(Long id) {
        Person person = personRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("User not found"));

        if(!person.isActive()) {
            throw new RuntimeException("User is already deactivated");
        }

        person.setActive(false);
        personRepository.save(person);
    }

}
