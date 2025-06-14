package com.scouthub.service;

import com.scouthub.dto.NotificationDto;
import com.scouthub.dto.OfferRequest;
import com.scouthub.dto.OfferResponse;
import com.scouthub.dto.OfferViewDto;
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
import java.util.stream.Collectors;

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

    @Override
    public List<NotificationDto> getNotificationsForPlayer(Long playerId) {
        return offerRepository.findByPlayerId(playerId).stream()
                .map(offer -> new NotificationDto(
                        offer.getId(),
                        offer.getScout().getName() + " " + offer.getScout().getSurname() + " sent you an offer.",
                        offer.getContractType(),
                        offer.getSalary(),
                        offer.getContactEmail(),
                        offer.getContactPhone(),
                        offer.getScout().getName() + offer.getScout().getSurname(),
                        offer.getPlayer().getName() +offer.getPlayer().getSurname(),
                        offer.getStatus().toString(),
                        offer.getTimestamp()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<NotificationDto> getNotificationsForScout(Long scoutId) {
        return offerRepository.findByScoutId(scoutId).stream()
                .filter(offer -> offer.getStatus() != Offer.Status.SENT)
                .map(offer -> new NotificationDto(
                        offer.getId(),
                        offer.getScout().getName() + " " + offer.getScout().getSurname() + " sent you an offer.",
                        offer.getContractType(),
                        offer.getSalary(),
                        offer.getPlayer().getEmail(),
                        offer.getPlayer().getPhone(),
                        offer.getScout().getName() + " " + offer.getScout().getSurname(),
                        offer.getPlayer().getName() + " " + offer.getPlayer().getSurname(),
                        offer.getStatus().toString(),
                        offer.getTimestamp()
                ))
                .collect(Collectors.toList());
    }

    @Override
    public List<OfferViewDto> getOffersForView(Long scoutId) {
        return offerRepository.findByScoutId(scoutId).stream()
                .map(offer -> {
                    OfferViewDto dto = new OfferViewDto();
                    dto.setId(offer.getId());
                    dto.setContractType(offer.getContractType());
                    dto.setSalary(offer.getSalary());
                    dto.setStatus(offer.getStatus().toString());
                    dto.setTimestamp(offer.getTimestamp());
                    dto.setPlayerName(offer.getPlayer().getName());
                    dto.setPlayerSurname(offer.getPlayer().getSurname());
                    dto.setPlayerClub(offer.getPlayer().getClub());
                    return dto;
                })
                .toList();
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

    public static NotificationDto toNotificationDto(Offer offer) {
        NotificationDto dto = new NotificationDto();
        dto.setOfferId(offer.getId());
        dto.setMessage(offer.getMessage());
        dto.setContractType(offer.getContractType());
        dto.setSalary(offer.getSalary());
        dto.setContactEmail(offer.getContactEmail());
        dto.setContactPhone(offer.getContactPhone());
        dto.setStatus(offer.getStatus().name());
        dto.setTimestamp(offer.getTimestamp());

        if (offer.getScout() != null) {
            dto.setSenderName(offer.getScout().getName() + " " + offer.getScout().getSurname());
        }
        if (offer.getPlayer() != null) {
            dto.setRecipientName(offer.getPlayer().getName() + " " + offer.getPlayer().getSurname());
        }

        return dto;
    }
}
