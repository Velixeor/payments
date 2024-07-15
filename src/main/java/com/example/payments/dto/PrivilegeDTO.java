package com.example.payments.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class PrivilegeDTO {
    private Integer id;
    private String title;
    private String code;
    private List<Integer> loyaltyLevelID;
}

