package com.scouthub.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OfferViewDto{
    private Long id;
    private String contractType;
    private int salary;
    private String status;
    private LocalDateTime timestamp;

    private String playerName;
    private String playerSurname;
    private String playerClub;
}
