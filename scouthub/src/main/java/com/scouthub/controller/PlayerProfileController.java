package com.scouthub.controller;

import com.scouthub.model.Player;
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

    @Autowired
    public PlayerProfileController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping("/profile")
    public String profilePage(Model model, Principal principal) {
        Player player = playerService.getPlayerByUsername(principal.getName());
        model.addAttribute("player", player);
        return "player/profile";
    }
}
