// PlayerServiceImpl.java
package com.scouthub.service;

import com.scouthub.dto.PlayerAndAverageStats;
import com.scouthub.dto.PlayerAverageStats;
import com.scouthub.dto.PlayerSearchFilters;
import com.scouthub.model.Player;
import com.scouthub.repository.PlayerRepository;
//import com.scouthub.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PlayerServiceImpl implements PlayerService {

    private final PlayerRepository playerRepository;
    private final MatchService matchService;

    @Autowired
    public PlayerServiceImpl(PlayerRepository playerRepository, MatchService matchService) {
        this.playerRepository = playerRepository;
        this.matchService = matchService;
    }

    @Override
    public Player createPlayer(Player player) {
        return playerRepository.save(player);
    }

    @Override
    public Optional<Player> getPlayerById(Long id) {
        return playerRepository.findById(id);
    }

    @Override
    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    @Override
    public Player updatePlayer(Long id, Player updatedPlayer) {
        updatedPlayer.setId(id);
        return playerRepository.save(updatedPlayer);
    }

    @Override
    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }

    @Override
    public Player getPlayerByUsername(String username) {
        return playerRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Player not found! !"));
    }

    @Override
    public List<PlayerAndAverageStats> searchPlayersWithStats(PlayerSearchFilters filters) {
        List<Player> allPlayers = playerRepository.findAll(); // later: optimize with dynamic query

        return allPlayers.stream()
                .filter(p -> filters.getUsername() == null || p.getUsername().toLowerCase().contains(filters.getUsername().toLowerCase()))
                .filter(p -> filters.getName() == null || p.getName().toLowerCase().contains(filters.getName().toLowerCase()))
                .filter(p -> filters.getSurname() == null || p.getSurname().toLowerCase().contains(filters.getSurname().toLowerCase()))
                .filter(p -> filters.getNationality() == null || p.getNationality().toLowerCase().contains(filters.getNationality().toLowerCase()))
                .filter(p -> filters.getClub() == null || p.getClub().toLowerCase().contains(filters.getClub().toLowerCase()))
                .filter(p -> filters.getGender() == null || p.getGender().toLowerCase().contains(filters.getGender().toLowerCase()))
                .filter(p -> filters.getPosition() == null || p.getPosition().toLowerCase().contains(filters.getPosition().toLowerCase()))
                .filter(p -> filters.getDateOfBirth() == null || p.getDateOfBirth().equals(filters.getDateOfBirth()))
                .map(player -> {
                    PlayerAverageStats stats = matchService.computePlayerAverageStats(player);
                    return new PlayerAndAverageStats(player, stats);
                })
                .filter(paas -> filters.isStatMatch(paas.getStats()))
                .collect(Collectors.toList());
    }
}
