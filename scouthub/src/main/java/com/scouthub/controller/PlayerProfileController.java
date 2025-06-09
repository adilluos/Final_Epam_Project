package com.scouthub.controller;

import com.scouthub.dto.PlayerAverageStats;
import com.scouthub.model.Player;
import com.scouthub.service.MatchService;
import com.scouthub.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/player")
public class PlayerProfileController {

    private final PlayerService playerService;
    private final MatchService matchService;

    @Autowired
    public PlayerProfileController(PlayerService playerService, MatchService matchService) {
        this.playerService = playerService;
        this.matchService = matchService;
    }

    @GetMapping("/profile")
    public String profilePage(Model model, Principal principal) {
        Player player = playerService.getPlayerByUsername(principal.getName());
        PlayerAverageStats averageStats = matchService.computePlayerAverageStats(player);
        model.addAttribute("player", player);
        model.addAttribute("averageStats", averageStats);
        return "player/profile";
    }
}
