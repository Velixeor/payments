package com.example.payments.dto;

import com.example.payments.entity.UserLoyaltyLevel;
import com.example.payments.entity.UserStatus;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class UserDTO {
    private Integer id;
    private String login;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private String mail;
    private String numberPhone;
    private Boolean isStaff;
    private LocalDateTime dateCreate;
    private Integer userStatusId;
    private Integer userLoyaltyLevelId;
}
