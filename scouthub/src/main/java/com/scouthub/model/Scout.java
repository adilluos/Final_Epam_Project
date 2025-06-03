package com.scouthub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "scouts")
public class Scout extends UserAccount{
    private String club;

    @ManyToMany
    @JoinTable(
            name = "target_list",
            joinColumns = @JoinColumn(name = "scout_id"),
            inverseJoinColumns = @JoinColumn(name = "player_id")
    )
    private Set<Player> targetList = new HashSet<>();

    @OneToMany(mappedBy = "scout", cascade = CascadeType.ALL)
    private List<Offer> offers = new ArrayList<>();
}
