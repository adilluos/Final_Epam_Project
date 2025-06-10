package com.scouthub.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "user_account")
public abstract class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Transient
    private String confirmPassword;

    @Column(nullable = false, unique = true)
    private String email;
    private String phone;

    private String name;
    private String surname;
    private LocalDate dateOfBirth;
    private String gender;
    private String nationality;

    @Enumerated(EnumType.STRING)
    private Role role;

    private boolean signed;

    public enum Role {
        PLAYER, SCOUT
    }
}
