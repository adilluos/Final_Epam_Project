package com.scouthub.service;

import com.scouthub.dto.PlayerRegistrationRequest;
import com.scouthub.dto.ScoutRegistrationRequest;
import com.scouthub.model.Player;
import com.scouthub.model.Scout;

public interface AuthService {
    Player registerPlayer(PlayerRegistrationRequest request);
    Scout registerScout(ScoutRegistrationRequest request);
}
