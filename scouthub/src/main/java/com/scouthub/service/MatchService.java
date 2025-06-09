package com.scouthub.service;

import com.scouthub.dto.PlayerAverageStats;
import com.scouthub.model.Match;
import com.scouthub.model.Player;

import java.util.List;
import java.util.Optional;

public interface MatchService {
    Match createMatch(Match match);
    Optional<Match> getMatchById(Long id);
    List<Match> getAllMatches();
    Match updateMatch(Long id, Match updatedMatch);
    void deleteMatch(Long id);
    PlayerAverageStats computePlayerAverageStats(Player player);
}
