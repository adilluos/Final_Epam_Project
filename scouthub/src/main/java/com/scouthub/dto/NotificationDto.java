package com.scouthub.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NotificationDto {
    private Long offerId;
    private String message;
    private String contractType;
    private int salary;

    private String contactEmail;
    private String contactPhone;

    private String senderName;
    private String recipientName;
    private String status;

    private LocalDateTime timestamp;
}
