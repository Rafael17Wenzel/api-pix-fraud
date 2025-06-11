package com.api.pix_fraud.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.pix_fraud.models.User;
import com.api.pix_fraud.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuditService auditService;

    public User registerUser(User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Usuário com e-mail já existe.");
        }

        User savedUser = userRepository.save(user);
        auditService.log(savedUser, "Registro de usuário", "Usuário registrado com e-mail: " + savedUser.getEmail());
        return savedUser;
    }

    public User findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        auditService.log(user, "Consulta de usuário por ID", "ID consultado: " + id);
        return user;
    }

    public User findUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuário não encontrado"));
        auditService.log(user, "Consulta de usuário por e-mail", "E-mail consultado: " + email);
        return user;
    }
}
