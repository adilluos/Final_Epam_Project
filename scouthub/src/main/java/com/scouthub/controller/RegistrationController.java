package com.scouthub.controller;

import com.scouthub.dto.PlayerRegistrationRequest;
import com.scouthub.dto.ScoutRegistrationRequest;
import com.scouthub.model.Player;
import com.scouthub.model.Scout;
import com.scouthub.model.UserAccount;
import com.scouthub.service.AuthService;
import com.scouthub.service.PlayerService;
import com.scouthub.service.ScoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("register")
public class RegistrationController {

    private final PlayerService playerService;
    private final ScoutService scoutService;
    private final AuthService authService;

    @Autowired
    public RegistrationController(PlayerService playerService, ScoutService scoutService, AuthService authService) {
        this.playerService = playerService;
        this.scoutService = scoutService;
        this.authService = authService;
    }

    @GetMapping
    public String chooseRole() {
        return "register/choose-role";
    }

    @GetMapping("/player")
    public String playerForm(Model model) {
        model.addAttribute("playerDto", new PlayerRegistrationRequest());
        return "register/player-form";
    }

    @GetMapping("/scout")
    public String scoutForm(Model model) {
        model.addAttribute("scoutDto", new ScoutRegistrationRequest());
        return "register/scout-form";
    }

    @PostMapping("/player")
    public String registerPlayer(@ModelAttribute("playerDto") PlayerRegistrationRequest dto,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        try {
            authService.registerPlayer(dto);
            return "redirect:/player/profile";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("playerDto", dto);  // re-fill form values
            return "register/player-form";
        } catch (Exception ex) {
            model.addAttribute("error", "Registration failed: " + extractMeaningfulMessage(ex));
        }

        model.addAttribute("playerDto", dto);
        return "register/player-form";
    }

    @PostMapping("/scout")
    public String registerScout(@ModelAttribute("scoutDto") ScoutRegistrationRequest dto,
                                Model model,
                                RedirectAttributes redirectAttributes) {
        try {
            authService.registerScout(dto);
            return "redirect:/scout/profile";
        } catch (IllegalArgumentException ex) {
            model.addAttribute("error", ex.getMessage());
            model.addAttribute("scoutDto", dto);  // re-fill form values
            return "register/scout-form";
        } catch (Exception ex) {
            model.addAttribute("error", "Registration failed: " + extractMeaningfulMessage(ex));
        }

        model.addAttribute("scoutDto", dto); // preserve form data
        return "register/scout-form";
    }

    private String extractMeaningfulMessage(Exception ex) {
        Throwable root = ex;
        while (root.getCause() != null) {
            root = root.getCause();
        }

        String msg = root.getMessage().toLowerCase();

        if (msg.contains("user_account.username") || msg.contains("unique constraint") && msg.contains("username")) {
            return "Username already exists.";
        } else if (msg.contains("user_account.email") || msg.contains("unique constraint") && msg.contains("email")) {
            return "Email already exists.";
        }

        return "Unexpected error occurred. Please try again.";
    }

}
