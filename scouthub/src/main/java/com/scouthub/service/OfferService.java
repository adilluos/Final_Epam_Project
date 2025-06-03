package com.scouthub.service;

import com.scouthub.model.Offer;

import java.util.List;
import java.util.Optional;

public interface OfferService {
    Offer createOffer(Offer offer);
    Optional<Offer> getOfferById(Long id);
    List<Offer> getAllOffers();
    Offer updateOffer(Long id, Offer updatedOffer);
    void deleteOffer(Long id);
}
