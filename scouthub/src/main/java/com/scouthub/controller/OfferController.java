package com.scouthub.controller;


import com.scouthub.dto.NotificationDto;
import com.scouthub.dto.OfferRequest;
import com.scouthub.dto.OfferResponse;
import com.scouthub.model.Offer;
import com.scouthub.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/offers")
public class OfferController {
    private final OfferService offerService;

    @Autowired
    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @PostMapping("/{scoutId}")
    public ResponseEntity<OfferResponse> sendOffer(@PathVariable Long scoutId, @RequestBody OfferRequest request) {
        OfferResponse created = offerService.createOffer(request, scoutId);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/scout/{scoutId}")
    public List<OfferResponse> scoutOffers(@PathVariable Long scoutId) {
        return offerService.getOffersByScout(scoutId);
    }

    @GetMapping("/player/{playerId}")
    public List<OfferResponse> playerOffers(@PathVariable Long playerId) {
        return offerService.getOffersByPlayer(playerId);
    }

    @PutMapping("/{offerId}/respond")
    public ResponseEntity<String> respondToOffer(@PathVariable Long offerId,
                                                 @RequestParam Offer.Status status,
                                                 @RequestParam Long playerId) {
        offerService.respondToOffer(offerId, status, playerId);
        return ResponseEntity.ok("Offer " + status.name().toLowerCase());
    }

    @GetMapping("/notifications/player/{playerId}")
    public List<NotificationDto> getPlayerNotifications(@PathVariable Long playerId) {
        return offerService.getNotificationsForPlayer(playerId);
    }

    @GetMapping("/notifications/scout/{scoutId}")
    public List<NotificationDto> getScoutNotifications(@PathVariable Long scoutId) {
        return offerService.getNotificationsForScout(scoutId);
    }
}
