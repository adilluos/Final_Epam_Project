package com.scouthub.security;

import com.scouthub.model.Player;
import com.scouthub.model.Scout;
import com.scouthub.repository.PlayerRepository;
import com.scouthub.repository.ScoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final PlayerRepository playerRepository;
    private final ScoutRepository scoutRepository;

    @Autowired
    public CustomUserDetailsService(PlayerRepository playerRepository, ScoutRepository scoutRepository) {
        this.playerRepository = playerRepository;
        this.scoutRepository = scoutRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Player> playerOpt = playerRepository.findByUsernameOrEmail(username, username);
        if (playerOpt.isPresent()) {
            Player player = playerOpt.get();
            return User.builder()
                    .username(player.getUsername())
                    .password(player.getPassword())
                    .roles("PLAYER")
                    .build();
        }

        Optional<Scout> scoutOpt = scoutRepository.findByUsernameOrEmail(username, username);
        if (scoutOpt.isPresent()) {
            Scout scout = scoutOpt.get();
            return User.builder()
                    .username(scout.getUsername())
                    .password(scout.getPassword())
                    .roles("SCOUT")
                    .build();
        }

        throw new UsernameNotFoundException("User not found with username: " + username);
    }
}
