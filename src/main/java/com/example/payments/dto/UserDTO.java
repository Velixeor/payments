package com.example.payments.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
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
    private ZonedDateTime dateCreate;
    private Integer userStatusId;
    private Integer userLoyaltyLevelId;
}
