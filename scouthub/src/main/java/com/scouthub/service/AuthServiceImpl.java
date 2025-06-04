package com.scouthub.service;

import com.scouthub.dto.LoginRequest;
import com.scouthub.dto.LoginResponse;
import com.scouthub.dto.PlayerRegistrationRequest;
import com.scouthub.dto.ScoutRegistrationRequest;
import com.scouthub.model.Player;
import com.scouthub.model.Scout;
import com.scouthub.model.UserAccount;
import com.scouthub.repository.PlayerRepository;
import com.scouthub.repository.ScoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService{
    private final PlayerRepository playerRepository;
    private final ScoutRepository scoutRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public AuthServiceImpl(PlayerRepository playerRepository, ScoutRepository scoutRepository) {
        this.playerRepository = playerRepository;
        this.scoutRepository = scoutRepository;
    }

    @Override
    public Player registerPlayer(PlayerRegistrationRequest request) {
        if (!request.getPassword().equals(request.getRepeatPassword())) {
            throw new IllegalArgumentException("Passwords do not match.");
        }
        Player player = new Player();
        player.setUsername(request.getUsername());
        player.setPassword(passwordEncoder.encode(request.getPassword()));
        player.setEmail(request.getEmail());
        player.setName(request.getName());
        player.setSurname(request.getSurname());
//        player.setDateOfBirth(LocalDate.parse(request.getDateOfBirth()));
        player.setDateOfBirth(request.getDateOfBirth());
        player.setGender(request.getGender());
        player.setNationality(request.getNationality());
        player.setClub(request.getClub());
        player.setPosition(request.getPosition());
        player.setPhone(request.getPhone());
        player.setRole(UserAccount.Role.PLAYER);
        player.setSigned(false);

        return playerRepository.save(player);
    }

    @Override
    public Scout registerScout(ScoutRegistrationRequest request) {
        if (!request.getPassword().equals(request.getRepeatPassword())) {
            throw new IllegalArgumentException("Passwords do not match.");
        }
        Scout scout = new Scout();
        scout.setUsername(request.getUsername());
        scout.setPassword(passwordEncoder.encode(request.getPassword()));
        scout.setEmail(request.getEmail());
        scout.setName(request.getName());
        scout.setSurname(request.getSurname());
        scout.setDateOfBirth(request.getDateOfBirth());
        scout.setGender(request.getGender());
        scout.setNationality(request.getNationality());
        scout.setClub(request.getClub());
        scout.setPhone(request.getPhone());
        scout.setRole(UserAccount.Role.SCOUT);
        scout.setSigned(false);

        return scoutRepository.save(scout);
    }

    @Override
    public LoginResponse login(LoginRequest request) {
        String usernameOrEmail = request.getUsernameOrEmail();
        String rawPassword = request.getPassword();

        Optional<? extends UserAccount> userOptional = playerRepository
                .findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                .map(user -> (UserAccount) user);

        if (userOptional.isEmpty()) {
            userOptional = scoutRepository
                    .findByUsernameOrEmail(usernameOrEmail, usernameOrEmail)
                    .map(user -> (UserAccount) user);
        }

        if (userOptional.isPresent()) {
            UserAccount user = userOptional.get();
            if (passwordEncoder.matches(rawPassword, user.getPassword())) {
                return new LoginResponse(true, "Login successful as " + user.getUsername() + " " + user.getRole());
            }
        }

        return new LoginResponse(false, "Invalid username/email or password");
    }
}
