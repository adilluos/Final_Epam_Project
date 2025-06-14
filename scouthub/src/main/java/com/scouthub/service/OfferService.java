package com.scouthub.service;

import com.scouthub.dto.NotificationDto;
import com.scouthub.dto.OfferRequest;
import com.scouthub.dto.OfferResponse;
import com.scouthub.dto.OfferViewDto;
import com.scouthub.model.Offer;

import java.util.List;

public interface OfferService {
    OfferResponse createOffer(OfferRequest request, Long scoutId);
//    Optional<Offer> getOfferById(Long id);
//    List<Offer> getAllOffers();
//    Offer updateOffer(Long id, Offer updatedOffer);
//    void deleteOffer(Long id);

    List<OfferResponse> getOffersByScout(Long scoutId);
    List<OfferResponse> getOffersByPlayer(Long playerId);
    void respondToOffer(Long offerId, Offer.Status status, Long playerId);
    List<NotificationDto> getNotificationsForPlayer(Long playerId);
    List<NotificationDto> getNotificationsForScout(Long scoutId);
    List<OfferViewDto> getOffersForView(Long scoutId);
}
