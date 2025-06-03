package com.scouthub.repository;

import com.scouthub.model.MatchStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchStatsRepository extends JpaRepository<MatchStats, Long> {
}
