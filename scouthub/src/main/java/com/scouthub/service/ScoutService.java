package com.scouthub.service;

import com.scouthub.model.Scout;

import java.util.List;
import java.util.Optional;

public interface ScoutService {
    Scout createScout(Scout scout);
    Optional<Scout> getScoutById(Long id);
    List<Scout> getAllScouts();
    Scout updateScout(Long id, Scout updatedScout);
    void deleteScout(Long id);
    Scout getScoutByUsername(String username);
}
