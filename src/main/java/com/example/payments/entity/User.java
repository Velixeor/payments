package com.example.payments.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user", schema = "auth")
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
    private String firstName;
    @Column(name = "middle_name", nullable = false)
    private String middleName;
    @Column(name = "last_name", nullable = false)
    private String lastName;
    @Column(name = "mail", nullable = false, unique = true)
    private String mail;
    @Column(name = "number_phone", nullable = false, unique = true)
    private String numberPhone;
    @Column(name = "is_staff", nullable = false)
    private Boolean isStaff;
    @Column(name = "date_create", nullable = false)
    private ZonedDateTime dateCreate;
    @Column(name = "date_update")
    private ZonedDateTime dateUpdate;
    @Enumerated(EnumType.STRING) //значение будет сохранено в базу как число
    @Column(name="status")
    private Status status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_loyalty_level_id", nullable = false)
    private UserLoyaltyLevel userLoyaltyLevel;


}
