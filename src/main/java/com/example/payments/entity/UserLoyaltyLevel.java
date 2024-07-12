package com.example.payments.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user_loyalty_level", schema = "auth")
public class UserLoyaltyLevel {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "code", nullable = false)
    private String code;
    @Column(name = "is_active", nullable = false)
    private Boolean is_active;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "user_loyalty_level_bonus",
            joinColumns = @JoinColumn(name = "user_loyalty_level_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "bonus_id", referencedColumnName = "id"))
    private List<Bonus> bonuses;
}
