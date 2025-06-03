package com.scouthub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "match_stats")
public class MatchStats {

    @Id
    private Long matchId;

    @MapsId
    @OneToOne
    @JoinColumn(name = "matchId")
    private Match match;

    private int goals;
    private int assists;
    private int passes;
    private int keyPasses;
    private double passCompletionRate;
    private double dribbleSuccessRate;
    private int tacklesWon;
    private int blocks;
    private double distanceCovered;
    private Integer saves;
    private Double goalsPrevented;
}
