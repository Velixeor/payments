package com.example.payments.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.ZonedDateTime;
import java.util.Collection;
import java.util.Collections;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user", schema = "auth")
public class User implements UserDetails {
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
    private boolean isStaff;
    @Column(name = "date_create", nullable = false)
    private ZonedDateTime dateCreate;
    @Column(name = "date_update")
    private ZonedDateTime dateUpdate;
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private Status status;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_loyalty_level_id", nullable = false)
    private UserLoyaltyLevel userLoyaltyLevel;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = isStaff ? "ROLE_STAFF" : "ROLE_USER";
        return Collections.singletonList(new SimpleGrantedAuthority(role));
    }

    @Override
    public String getUsername() {
        return login;
    }

}
