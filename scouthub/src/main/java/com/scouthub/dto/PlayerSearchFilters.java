package com.scouthub.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class PlayerSearchFilters {
    private String username;
    private String name;
    private String surname;
    private String nationality;
    private String club;
    private LocalDate dateOfBirth;
    private String gender;
    private String position;

    private Double minAvgGoals;
    private Double minAvgAssists;
    private Double minAvgPasses;
    private Double minAvgKeyPasses;
    private Double minAvgPassCompletionRate;
    private Double minAvgDribbleSuccessRate;
    private Double minAvgTacklesWon;
    private Double minAvgBlocks;
    private Double minAvgDistanceCovered;
    private Double minAvgSaves;
    private Double minAvgGoalsPrevented;

    public boolean isStatMatch(PlayerAverageStats stats) {
        if (stats == null) return false;

        if (minAvgGoals != null && stats.getGoals() < minAvgGoals) return false;
        if (minAvgAssists != null && stats.getAssists() < minAvgAssists) return false;
        if (minAvgPasses != null && stats.getPasses() < minAvgPasses) return false;
        if (minAvgKeyPasses != null && stats.getKeyPasses() < minAvgKeyPasses) return false;
        if (minAvgPassCompletionRate != null && stats.getPassCompletionRate() < minAvgPassCompletionRate) return false;
        if (minAvgDribbleSuccessRate != null && stats.getDribbleSuccessRate() < minAvgDribbleSuccessRate) return false;
        if (minAvgTacklesWon != null && stats.getTacklesWon() < minAvgTacklesWon) return false;
        if (minAvgBlocks != null && stats.getBlocks() < minAvgBlocks) return false;
        if (minAvgDistanceCovered != null && stats.getDistanceCovered() < minAvgDistanceCovered) return false;

        // Only check GK stats if they're present
        if (minAvgSaves != null && stats.getSaves() != null && stats.getSaves() < minAvgSaves) return false;
        if (minAvgGoalsPrevented != null && stats.getGoalsPrevented() != null && stats.getGoalsPrevented() < minAvgGoalsPrevented) return false;

        return true;
    }

}
