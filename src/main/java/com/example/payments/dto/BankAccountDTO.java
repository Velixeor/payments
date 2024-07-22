package com.example.payments.dto;


import com.example.payments.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class BankAccountDTO {
    private Integer id;
    private String code;
    private ZonedDateTime dateCreate;
    private Status status;
    private String currency;
    private Integer balance;
    private Integer userID;
}
