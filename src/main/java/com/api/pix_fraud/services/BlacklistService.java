package com.api.pix_fraud.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.api.pix_fraud.models.Blacklist;
import com.api.pix_fraud.models.User;
import com.api.pix_fraud.models.Whitelist;
import com.api.pix_fraud.repositories.BlacklistRepository;
import com.api.pix_fraud.repositories.UserRepository;
import com.api.pix_fraud.repositories.WhitelistRepository;

@Service
public class BlacklistService {

    private final BlacklistRepository blacklistRepository;
    private final WhitelistRepository whitelistRepository;
    private final UserRepository userRepository;

    public BlacklistService(BlacklistRepository blacklistRepository, WhitelistRepository whitelistRepository, UserRepository userRepository) {
        this.blacklistRepository = blacklistRepository;
        this.whitelistRepository = whitelistRepository;
        this.userRepository = userRepository;
    }

    public User putUserIntoTheBlacklist(Long id) {
        Optional<User> existingUser = userRepository.findById(id);
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

        return existingUser.get();
    }

    public List<User> getBlacklistUsers() {
        List<Blacklist> blacklist = blacklistRepository.findAll();

        return blacklist.stream()
                        .map(Blacklist::getUser)
                        .collect(Collectors.toList());
    }
    
}
