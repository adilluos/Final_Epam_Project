package com.scouthub.service;

import com.scouthub.model.Match;
import com.scouthub.model.MatchStats;
import com.scouthub.repository.MatchRepository;
import com.scouthub.repository.MatchStatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MatchServiceImpl implements MatchService{

    private final MatchRepository matchRepository;
    private final MatchStatsRepository matchStatsRepository;

    @Autowired
    public MatchServiceImpl(MatchRepository matchRepository, MatchStatsRepository matchStatsRepository) {
        this.matchRepository = matchRepository;
        this.matchStatsRepository = matchStatsRepository;
    }

    @Override
    public Match createMatch(Match match) {
        MatchStats stats = match.getStats();
        match.setStats(null);
        Match savedMatch = matchRepository.save(match);
        if (stats != null) {
            stats.setMatch(savedMatch);
            savedMatch.setStats(stats);
            matchStatsRepository.save(stats);
        }
        return savedMatch;
    }

    @Override
    public Optional<Match> getMatchById(Long id) {
        return matchRepository.findById(id);
    }

    @Override
    public List<Match> getAllMatches() {
        return matchRepository.findAll();
    }

    @Override
    public Match updateMatch(Long id, Match updatedMatch) {
        updatedMatch.setId(id);
        return matchRepository.save(updatedMatch);
    }

    @Override
    public void deleteMatch(Long id) {
        matchRepository.deleteById(id);
    }
}
