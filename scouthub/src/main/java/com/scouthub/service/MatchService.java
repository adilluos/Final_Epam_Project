package com.scouthub.service;

import com.scouthub.model.Match;

import java.util.List;
import java.util.Optional;

public interface MatchService {
    Match createMatch(Match match);
    Optional<Match> getMatchById(Long id);
    List<Match> getAllMatches();
    Match updateMatch(Long id, Match updatedMatch);
    void deleteMatch(Long id);
}
