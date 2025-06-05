package com.scouthub.dto;

import lombok.Data;

@Data
public class OfferRequest {
    private Long playerId;
    private String message;
    private int salary;
    private String contractType;
    private String contactEmail;
    private String contactPhone;
}
