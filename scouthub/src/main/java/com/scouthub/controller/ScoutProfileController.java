package com.scouthub.controller;

import com.scouthub.model.Scout;
import com.scouthub.service.ScoutService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/scout")
public class ScoutProfileController {

    private final ScoutService scoutService;

    @Autowired
    public ScoutProfileController(ScoutService scoutService) {
        this.scoutService = scoutService;
    }

    @GetMapping("/profile")
    public String profilePage(Model model, Principal principal) {
        Scout scout = scoutService.getScoutByUsername(principal.getName());
        model.addAttribute("scout", scout);
        return "scout/profile";
    }
}
