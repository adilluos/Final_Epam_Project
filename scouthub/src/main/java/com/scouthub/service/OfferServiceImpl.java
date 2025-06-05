package com.scouthub.service;

import com.scouthub.dto.OfferRequest;
import com.scouthub.dto.OfferResponse;
import com.scouthub.model.Offer;
import com.scouthub.model.Player;
import com.scouthub.model.Scout;
import com.scouthub.repository.OfferRepository;
import com.scouthub.repository.PlayerRepository;
import com.scouthub.repository.ScoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OfferServiceImpl implements OfferService{

    private final OfferRepository offerRepository;
    private final PlayerRepository playerRepository;
    private final ScoutRepository scoutRepository;

    @Autowired
    public OfferServiceImpl(OfferRepository offerRepository, PlayerRepository playerRepository, ScoutRepository scoutRepository) {
        this.offerRepository = offerRepository;
        this.playerRepository = playerRepository;
        this.scoutRepository = scoutRepository;
    }

    @Override
    public OfferResponse createOffer(OfferRequest request, Long scoutId) {
        Player player = playerRepository.findById(request.getPlayerId())
                .orElseThrow(() -> new IllegalArgumentException("Player not found"));
        Scout scout = scoutRepository.findById(scoutId)
                .orElseThrow(() -> new IllegalArgumentException("Scout not found"));
        Offer offer = new Offer();
        offer.setPlayer(player);
        offer.setScout(scout);
        offer.setMessage(request.getMessage());
        offer.setSalary(request.getSalary());
        offer.setContractType(request.getContractType());
        offer.setContactEmail(request.getContactEmail());
        offer.setContactPhone(request.getContactPhone());

        Offer savedOffer = offerRepository.save(offer);
        return toDto(savedOffer);
    }

//    @Override
//    public Optional<Offer> getOfferById(Long id) {
//        return offerRepository.findById(id);
//    }
//
//    @Override
//    public List<Offer> getAllOffers() {
//        return offerRepository.findAll();
//    }
//
//    @Override
//    public Offer updateOffer(Long id, Offer updatedOffer) {
//        updatedOffer.setId(id);
//        return offerRepository.save(updatedOffer);
//    }
//
//    @Override
//    public void deleteOffer(Long id) {
//        offerRepository.deleteById(id);
//    }

    @Override
    public List<OfferResponse> getOffersByScout(Long scoutId) {
        return offerRepository.findByScoutId(scoutId).stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<OfferResponse> getOffersByPlayer(Long playerId) {
        return offerRepository.findByPlayerId(playerId).stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public void respondToOffer(Long offerId, Offer.Status status, Long playerId) {
        Offer offer = offerRepository.findById(offerId)
                .orElseThrow(() -> new IllegalArgumentException("Offer not found"));

        if (!offer.getPlayer().getId().equals(playerId)) {
            throw new SecurityException("You are not authorized to respond to this offer");
        }

        if (offer.getStatus() != Offer.Status.SENT) {
            throw new IllegalStateException("Offer already responded to");
        }

        if (status != Offer.Status.ACCEPTED && status != Offer.Status.DECLINED) {
            throw new IllegalArgumentException("Invalid response status");
        }

        offer.setStatus(status);
        offerRepository.save(offer);
    }

    private OfferResponse toDto(Offer offer) {
        OfferResponse dto = new OfferResponse();
        dto.setId(offer.getId());
        dto.setSalary(offer.getSalary());
        dto.setContractType(offer.getContractType());
        dto.setMessage(offer.getMessage());
        dto.setContactEmail(offer.getContactEmail());
        dto.setContactPhone(offer.getContactPhone());
        dto.setStatus(String.valueOf(offer.getStatus()));
        dto.setTimestamp(offer.getTimestamp());
        dto.setScoutId(offer.getScout().getId());
        dto.setPlayerId(offer.getPlayer().getId());
        return dto;
    }
}
