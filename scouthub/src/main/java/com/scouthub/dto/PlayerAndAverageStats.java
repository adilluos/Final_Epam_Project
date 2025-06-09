package com.scouthub.dto;

import com.scouthub.model.Player;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayerAndAverageStats {
    private Player player;
    private PlayerAverageStats stats;
}
