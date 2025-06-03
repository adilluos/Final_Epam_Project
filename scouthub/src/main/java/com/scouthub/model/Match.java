package com.scouthub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "matches")
public class Match {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate matchDate;
    private String tournament;
    private String ownTeam;
    private String opponentTeam;
    private boolean isGoalkeeper;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    @OneToOne(mappedBy = "match", cascade = CascadeType.ALL)
    private MatchStats stats;
}
