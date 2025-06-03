package com.scouthub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "offers")
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int salary;
    private String contractType;
    private String message;
    private String contactEmail;
    private String contactPhone;

    @Enumerated(EnumType.STRING)
    private Status status = Status.SENT;

    private LocalDateTime timestamp = LocalDateTime.now();

    @ManyToOne
    private Scout scout;

    @ManyToOne
    private Player player;

    public enum Status {
        SENT, ACCEPTED, DECLINED
    }
}
