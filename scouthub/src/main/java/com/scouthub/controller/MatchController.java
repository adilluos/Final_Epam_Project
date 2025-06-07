package com.scouthub.controller;

import com.scouthub.model.Match;
import com.scouthub.model.MatchStats;
import com.scouthub.model.Player;
import com.scouthub.service.MatchService;
import com.scouthub.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/player/match")
public class MatchController {

    private final MatchService matchService;
    private final PlayerService playerService;

    @Autowired
    public MatchController(MatchService matchService, PlayerService playerService) {
        this.matchService = matchService;
        this.playerService = playerService;
    }

    @GetMapping("/new")
    public String showMatchForm(Model model, Principal principal) {
        String username = principal.getName();
        Player player = playerService.getPlayerByUsername(username);
        Match match = new Match();
        match.setStats(new MatchStats());
        match.setPlayer(player);
        match.setGoalkeeper("goalkeeper".equalsIgnoreCase(player.getPosition()));
        model.addAttribute("match", match);
        return "player/add-match";
    }

    @PostMapping("/new")
    public String submitMatch(@ModelAttribute("match") Match match, Principal principal) {
        String username = principal.getName();
        Player player = playerService.getPlayerByUsername(username);
        match.setPlayer(player);
        matchService.createMatch(match);
        return "redirect:/player/profile";
    }
}
