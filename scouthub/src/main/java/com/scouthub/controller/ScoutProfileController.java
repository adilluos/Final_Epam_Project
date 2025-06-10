package com.scouthub.controller;

import com.scouthub.dto.*;
import com.scouthub.model.Scout;
import com.scouthub.service.OfferService;
import com.scouthub.service.PlayerService;
import com.scouthub.service.ScoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/scout")
public class ScoutProfileController {

    private final ScoutService scoutService;
    private final PlayerService playerService;
    private final OfferService offerService;

    @Autowired
    public ScoutProfileController(ScoutService scoutService, PlayerService playerService, OfferService offerService) {
        this.scoutService = scoutService;
        this.playerService = playerService;
        this.offerService = offerService;
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

    @GetMapping("offer/new")
    public String offerForm(@RequestParam("playerId") Long playerId, Model model) {
        OfferRequest offerRequest = new OfferRequest();
        offerRequest.setPlayerId(playerId);
        model.addAttribute("offerRequest", offerRequest);
        return "scout/offer-form";
    }

    @PostMapping("offer/send")
    public String submitOffer(@ModelAttribute OfferRequest offerRequest, Principal principal) {
        Scout scout = scoutService.getScoutByUsername(principal.getName());
        offerRequest.setContactEmail(scout.getEmail());
        offerRequest.setContactPhone(scout.getPhone());
        offerService.createOffer(offerRequest, scout.getId());
        return "redirect:/scout/profile?offerSent";
    }

    @GetMapping("target-list")
    public String targetListPage(Model model, Principal principal) {
        Scout scout = scoutService.getScoutByUsername(principal.getName());
        List<OfferViewDto> offers = offerService.getOffersForView(scout.getId());
        model.addAttribute("offers", offers);
        return "scout/target-list";
    }

    @GetMapping("/notifications")
    public String notificationPage(Model model, Principal principal) {
        Scout scout = scoutService.getScoutByUsername(principal.getName());
        List<NotificationDto> notifications = offerService.getNotificationsForScout(scout.getId());
        model.addAttribute("notifications", notifications);
        return "scout/notifications";
    }
}
