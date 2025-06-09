package com.scouthub.dto;

import lombok.Data;

@Data
public class PlayerAverageStats {
    public double goals;
    public double assists;
    public double passes;
    public double keyPasses;
    public double passCompletionRate;
    public double dribbleSuccessRate;
    public double tacklesWon;
    public double blocks;
    public double distanceCovered;
    public Double saves; // nullable
    public Double goalsPrevented;
}
