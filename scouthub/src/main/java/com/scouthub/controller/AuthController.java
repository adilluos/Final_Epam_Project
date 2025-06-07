package com.scouthub.controller;

import com.scouthub.dto.LoginRequest;
import com.scouthub.dto.LoginResponse;
import com.scouthub.dto.PlayerRegistrationRequest;
import com.scouthub.dto.ScoutRegistrationRequest;
import com.scouthub.model.Player;
import com.scouthub.model.Scout;
import com.scouthub.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register/player")
    public ResponseEntity<?> registerPlayer(@RequestBody PlayerRegistrationRequest request) {
        Player created = authService.registerPlayer(request);
        return ResponseEntity.ok(created);
    }

    @PostMapping("/register/scout")
    public ResponseEntity<?> registerScout(@RequestBody ScoutRegistrationRequest request) {
        Scout created = authService.registerScout(request);
        return ResponseEntity.ok(created);
    }

//    @PostMapping("/login")
//    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
//        LoginResponse responce = authService.login(request);
//        return responce.isSuccess() ? ResponseEntity.ok(responce) : ResponseEntity.status(401).body(responce);
//    }

}
