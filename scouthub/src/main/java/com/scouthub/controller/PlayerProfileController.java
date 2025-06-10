package com.scouthub.controller;

import com.scouthub.dto.OfferResponse;
import com.scouthub.dto.PlayerAverageStats;
import com.scouthub.model.Offer;
import com.scouthub.model.Player;
import com.scouthub.service.MatchService;
import com.scouthub.service.OfferService;
import com.scouthub.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/player")
public class PlayerProfileController {

    private final PlayerService playerService;
    private final MatchService matchService;
    private final OfferService offerService;

    @Autowired
    public PlayerProfileController(PlayerService playerService, MatchService matchService, OfferService offerService) {
        this.playerService = playerService;
        this.matchService = matchService;
        this.offerService = offerService;
    }

    @GetMapping("/profile")
    public String profilePage(Model model, Principal principal) {
        Player player = playerService.getPlayerByUsername(principal.getName());
        PlayerAverageStats averageStats = matchService.computePlayerAverageStats(player);
        model.addAttribute("player", player);
        model.addAttribute("averageStats", averageStats);
        return "player/profile";
    }

    @GetMapping("/notifications")
    public String notificationPage(Model model, Principal principal) {
        Player player = playerService.getPlayerByUsername(principal.getName());
        List<OfferResponse> offers = offerService.getOffersByPlayer(player.getId());
        model.addAttribute("offers", offers);
        return "player/notifications";
    }

    @PostMapping("/offers/{offerId}/respond")
    public String respondToOffer(@PathVariable Long offerId,
                                 @RequestParam("action") String action,
                                 Principal principal) {
        Player player = playerService.getPlayerByUsername(principal.getName());
        Offer.Status status = action.equals("accept") ? Offer.Status.ACCEPTED : Offer.Status.DECLINED;
        offerService.respondToOffer(offerId, status, player.getId());
        return "redirect:/player/notifications";
    }
}
