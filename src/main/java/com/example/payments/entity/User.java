package com.example.payments.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "login", nullable = false, unique = true)
    private String login;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "first_name", nullable = false)
    private String first_name;
    @Column(name = "middle_name", nullable = false)
    private String middle_name;
    @Column(name = "last_name", nullable = false)
    private String last_name;
    @Column(name = "mail", nullable = false, unique = true)
    private String mail;
    @Column(name = "number_phone", nullable = false, unique = true)
    private String number_phone;
    @Column(name = "is_staff", nullable = false)
    private Boolean is_staff;
    @Column(name = "date_create", nullable = false)
    private LocalDateTime date_create;
    @Column(name = "date_delete")
    private LocalDateTime date_delete;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_status_id", nullable = false)
    private UserStatus user;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_loyalty_level_id", nullable = false)
    private UserLoyaltyLevel user_loyalty_level_id;


}
