package com.example.payments.dto;


import com.example.payments.entity.Status;
import com.example.payments.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {
    private Integer id;
    private String login;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private String mail;
    private String numberPhone;
    private boolean isStaff;
    private ZonedDateTime dateCreate;
    private Status status;
    private Integer userLoyaltyLevelId;

    public static UserDTO from(User user, boolean includeSensitiveData) {
        return UserDTO.builder()
                .id(user.getId())
                .login(user.getLogin())
                .password(includeSensitiveData ? user.getPassword() : null)
                .firstName(user.getFirstName())
                .middleName(user.getMiddleName())
                .lastName(user.getLastName())
                .mail(user.getMail())
                .numberPhone(user.getNumberPhone())
                .isStaff(user.isStaff())
                .dateCreate(user.getDateCreate())
                .status(user.getStatus())
                .userLoyaltyLevelId(user.getUserLoyaltyLevel().getId())
                .build();
    }
}
