package com.scouthub.repository;

import com.scouthub.model.Scout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ScoutRepository extends JpaRepository<Scout, Long> {
    Optional<Scout> findByUsernameOrEmail(String username, String email);
}
