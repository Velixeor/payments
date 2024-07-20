package com.example.payments.dto;


import com.example.payments.entity.Status;
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
    private Boolean isStaff;
    private ZonedDateTime dateCreate;
    private Status status;
    private Integer userLoyaltyLevelId;
}
