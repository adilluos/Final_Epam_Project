// PlayerService.java
package com.scouthub.service;

import com.scouthub.dto.PlayerAndAverageStats;
import com.scouthub.dto.PlayerSearchFilters;
import com.scouthub.model.Player;
import java.util.List;
import java.util.Optional;

public interface PlayerService {
    Player createPlayer(Player player);
    Optional<Player> getPlayerById(Long id);
    List<Player> getAllPlayers();
    Player updatePlayer(Long id, Player updatedPlayer);
    void deletePlayer(Long id);
    Player getPlayerByUsername(String username);
    List<PlayerAndAverageStats> searchPlayersWithStats(PlayerSearchFilters filters);
}
