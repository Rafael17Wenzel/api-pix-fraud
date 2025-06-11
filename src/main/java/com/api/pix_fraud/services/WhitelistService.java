package com.api.pix_fraud.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.api.pix_fraud.models.Whitelist;
import com.api.pix_fraud.models.Blacklist;
import com.api.pix_fraud.models.User;
import com.api.pix_fraud.repositories.WhitelistRepository;
import com.api.pix_fraud.repositories.BlacklistRepository;
import com.api.pix_fraud.repositories.UserRepository;

@Service
public class WhitelistService {

    private final WhitelistRepository whitelistRepository;
    private final BlacklistRepository blacklistRepository;
    private final UserRepository userRepository;
    private final AuditService auditService;

    public WhitelistService(
        WhitelistRepository whitelistRepository,
        BlacklistRepository blacklistRepository,
        UserRepository userRepository,
        AuditService auditService
    ) {
        this.whitelistRepository = whitelistRepository;
        this.blacklistRepository = blacklistRepository;
        this.userRepository = userRepository;
        this.auditService = auditService;
    }

    public User putUserIntoTheWhitelist(Long id) {
        Optional<User> existingUser = userRepository.findById(id);
        if (!existingUser.isPresent()) {
            throw new RuntimeException("Usuário não existe");
        }

        Optional<Whitelist> userInWhitelist = whitelistRepository.findByUserId(id);
        if (userInWhitelist.isPresent()) {
            throw new RuntimeException("Usuário já está inserido na Whitelist");
        }

        Optional<Blacklist> existsInBlacklist = blacklistRepository.findByUserId(id);
        if (existsInBlacklist.isPresent()) {
            throw new RuntimeException("Usuário existe na blacklist");
        }

        Whitelist whitelist = new Whitelist();
        whitelist.setUser(existingUser.get());
        whitelistRepository.save(whitelist);

        auditService.log(
            existingUser.get(),
            "Inserção na whitelist",
            "Usuário inserido na whitelist, ID: " + id
        );

        return existingUser.get();
    }

    public List<User> getWhitelistUsers() {
        List<Whitelist> whitelist = whitelistRepository.findAll();

        auditService.log(
            null,
            "Consulta whitelist",
            "Consulta de todos os usuários na whitelist"
        );

        return whitelist.stream()
                        .map(Whitelist::getUser)
                        .collect(Collectors.toList());
    }
    
}
