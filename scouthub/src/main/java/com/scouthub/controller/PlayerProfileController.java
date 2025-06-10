package com.scouthub.controller;

import com.scouthub.dto.OfferResponse;
import com.scouthub.dto.PlayerAverageStats;
import com.scouthub.model.Offer;
import com.scouthub.model.Player;
import com.scouthub.service.MatchService;
import com.scouthub.service.OfferService;
import com.scouthub.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/player")
public class PlayerProfileController {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

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

    @GetMapping("/edit-profile")
    public String editForm(Model model, Principal principal) {
        Player player = playerService.getPlayerByUsername(principal.getName());
        model.addAttribute("player", player);
        return "player/edit-profile";
    }

    @PostMapping("/edit-profile")
    public String processEdit(@ModelAttribute("player") Player formPlayer,
                              @RequestParam(required = false) String newPassword,
                              @RequestParam(required = false) String repeatPassword,
                              Principal principal,
                              RedirectAttributes redirectAttributes) {
        Player existingPlayer = playerService.getPlayerByUsername(principal.getName());
        if (formPlayer.getName() != null) existingPlayer.setName(formPlayer.getName());
        if (formPlayer.getSurname() != null) existingPlayer.setSurname(formPlayer.getSurname());
        if (formPlayer.getDateOfBirth() != null) existingPlayer.setDateOfBirth(formPlayer.getDateOfBirth());
        if (formPlayer.getGender() != null) existingPlayer.setGender(formPlayer.getGender());
        if (formPlayer.getNationality() != null) existingPlayer.setNationality(formPlayer.getNationality());
        if (formPlayer.getClub() != null) existingPlayer.setClub(formPlayer.getClub());
        if (formPlayer.getPosition() != null) existingPlayer.setPosition(formPlayer.getPosition());

        if (newPassword != null && !newPassword.isBlank()) {
            if (!newPassword.equals(repeatPassword)) {
                redirectAttributes.addFlashAttribute("error", "Passwords do not match.");
                return "redirect:/player/edit";
            }
            existingPlayer.setPassword(passwordEncoder.encode(newPassword));
        }

        playerService.updatePlayer(existingPlayer.getId(), existingPlayer);
        redirectAttributes.addFlashAttribute("success", "Profile updated successfully!");
        return "redirect:/player/profile";

    }
}
