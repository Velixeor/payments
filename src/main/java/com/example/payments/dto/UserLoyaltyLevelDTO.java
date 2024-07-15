package com.example.payments.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class UserLoyaltyLevelDTO {
    private Integer id;
    private String title;
    private String code;
    private Boolean isActive;
    private List<Integer> privilegesID;
}
