package com.scouthub.controller;

import com.scouthub.dto.PlayerAndAverageStats;
import com.scouthub.dto.PlayerSearchFilters;
import com.scouthub.model.Scout;
import com.scouthub.service.PlayerService;
import com.scouthub.service.ScoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/scout")
public class ScoutProfileController {

    private final ScoutService scoutService;
    private final PlayerService playerService;

    @Autowired
    public ScoutProfileController(ScoutService scoutService, PlayerService playerService) {
        this.scoutService = scoutService;
        this.playerService = playerService;
    }

    @GetMapping("/profile")
    public String profilePage(Model model, Principal principal) {
        Scout scout = scoutService.getScoutByUsername(principal.getName());
        model.addAttribute("scout", scout);
        return "scout/profile";
    }

    @GetMapping("/search")
    public String searchPage(Model model) {
        model.addAttribute("filters", new PlayerSearchFilters());
        return "scout/search-player";
    }

    @GetMapping("/search/results")
    public String searchResultsPage(@ModelAttribute("filters") PlayerSearchFilters filters, Model model) {
        List<PlayerAndAverageStats> filteredPlayers = playerService.searchPlayersWithStats(filters);
        model.addAttribute("players", filteredPlayers);
        return "scout/search-results-player";
    }
}
