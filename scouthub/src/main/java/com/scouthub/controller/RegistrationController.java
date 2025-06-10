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
                                 RedirectAttributes redirectAttributes) {
        try {
            authService.registerPlayer(dto);
            redirectAttributes.addFlashAttribute("success", "Registration successful!");
            return "redirect:/player/profile";
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/register/player";
        }
    }

    @PostMapping("/scout")
    public String registerScout(@ModelAttribute("scoutDto") ScoutRegistrationRequest dto,
                                RedirectAttributes redirectAttributes) {
        try {
            authService.registerScout(dto);
            redirectAttributes.addFlashAttribute("success", "Registration successful!");
            return "redirect:/scout/profile";
        } catch (IllegalArgumentException ex) {
            redirectAttributes.addFlashAttribute("error", ex.getMessage());
            return "redirect:/scout/player";
        }
    }
}
