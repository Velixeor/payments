package com.example.payments.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class MoneyTransferDTO {
    Integer from;
    Integer to;
    String currency;
    Integer amount;

}
