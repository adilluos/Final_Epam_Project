package com.scouthub.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OfferResponse {
    private Long id;
    private String message;
    private String contactEmail;
    private String contactPhone;
    private String contractType;
    private int salary;
    private String status;
    private LocalDateTime timestamp;
    private Long playerId;
    private Long scoutId;
}
