package com.api.pix_fraud.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.api.pix_fraud.models.Blacklist;
import com.api.pix_fraud.models.Person;
import com.api.pix_fraud.models.Whitelist;
import com.api.pix_fraud.repositories.BlacklistRepository;
import com.api.pix_fraud.repositories.PersonRepository;
import com.api.pix_fraud.repositories.WhitelistRepository;

@Service
public class BlacklistService {

    private final BlacklistRepository blacklistRepository;
    private final WhitelistRepository whitelistRepository;
    private final PersonRepository userRepository;
    private final AuditService auditService;

    public BlacklistService(
        BlacklistRepository blacklistRepository,
        WhitelistRepository whitelistRepository,
        PersonRepository userRepository,
        AuditService auditService
    ) {
        this.blacklistRepository = blacklistRepository;
        this.whitelistRepository = whitelistRepository;
        this.userRepository = userRepository;
        this.auditService = auditService;
    }

    public Person putUserIntoTheBlacklist(Long id) {
        Optional<Person> existingUser = userRepository.findById(id);
        if (!existingUser.isPresent()) {
            throw new RuntimeException("Usuário não existe");
        }

        Optional<Blacklist> userInBlacklist = blacklistRepository.findByUserId(id);
        if (userInBlacklist.isPresent()) {
            throw new RuntimeException("Usuário já está inserido na blacklist");
        }

        Optional<Whitelist> existsInWhitelist = whitelistRepository.findByUserId(id);
        if (existsInWhitelist.isPresent()) {
            throw new RuntimeException("Usuário existe na whitelist");
        }

        Blacklist blacklist = new Blacklist();
        blacklist.setUser(existingUser.get());
        blacklistRepository.save(blacklist);

        auditService.log(
            existingUser.get(),
            "Inserção na blacklist",
            "Usuário inserido na blacklist, ID: " + id
        );

        return existingUser.get();
    }

    public List<Person> getBlacklistUsers() {
        List<Blacklist> blacklist = blacklistRepository.findAll();

        auditService.log(
            null,
            "Consulta blacklist",
            "Consulta de todos os usuários na blacklist"
        );

        return blacklist.stream()
                        .map(Blacklist::getUser)
                        .collect(Collectors.toList());
    }
    
}
