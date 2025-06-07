package com.scouthub.repository;

import com.scouthub.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findByUsernameOrEmail(String username, String email);
    Optional<Player> findByUsername(String username);
}
