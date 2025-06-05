package com.scouthub.repository;

import com.scouthub.model.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {
    List<Offer> findByScoutId(Long scoutId);
    List<Offer> findByPlayerId(Long playerId);
}
