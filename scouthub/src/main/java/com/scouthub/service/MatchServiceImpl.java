package com.scouthub.service;

import com.scouthub.dto.PlayerAverageStats;
import com.scouthub.model.Match;
import com.scouthub.model.MatchStats;
import com.scouthub.model.Player;
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

    @Override
    public PlayerAverageStats computePlayerAverageStats(Player player) {
        List<Match> matches = player.getMatches();
        if (matches == null || matches.isEmpty()) return new PlayerAverageStats();

        PlayerAverageStats avg = new PlayerAverageStats();
        int count = 0, savesCount = 0;
        for (Match match : matches) {
            MatchStats s = match.getStats();
            if (s == null) continue;

            count++;
            avg.goals += s.getGoals();
            avg.assists += s.getAssists();
            avg.passes += s.getPasses();
            avg.keyPasses += s.getKeyPasses();
            avg.passCompletionRate += s.getPassCompletionRate();
            avg.dribbleSuccessRate += s.getDribbleSuccessRate();
            avg.tacklesWon += s.getTacklesWon();
            avg.blocks += s.getBlocks();
            avg.distanceCovered += s.getDistanceCovered();

            if (s.getSaves() != null && s.getGoalsPrevented() != null) {
                savesCount++;
                avg.saves = (avg.saves == null ? 0 : avg.saves) + s.getSaves();
                avg.goalsPrevented = (avg.goalsPrevented == null ? 0.0 : avg.goalsPrevented) + s.getGoalsPrevented();
            }
        }

        if (count > 0) {
            avg.goals /= count;
            avg.assists /= count;
            avg.passes /= count;
            avg.keyPasses /= count;
            avg.passCompletionRate /= count;
            avg.dribbleSuccessRate /= count;
            avg.tacklesWon /= count;
            avg.blocks /= count;
            avg.distanceCovered /= count;
        }

        if (savesCount > 0) {
            avg.saves /= savesCount;
            avg.goalsPrevented /= savesCount;
        }

        return avg;
    }
}
