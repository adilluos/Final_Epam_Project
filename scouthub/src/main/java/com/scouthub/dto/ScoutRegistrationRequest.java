package com.scouthub.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ScoutRegistrationRequest {
    private String username;
    private String password;
    private String repeatPassword;
    private String email;
    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private String gender;
    private String nationality;
    private String club;
    private String phone;
}
