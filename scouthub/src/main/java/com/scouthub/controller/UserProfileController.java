package com.scouthub.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
public class UserProfileController {
    @GetMapping("/profile")
    public String showProfile(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        return "profile";
    }
}
