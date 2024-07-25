package com.example.payments.dto;


import com.example.payments.entity.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCoreDTO {
    private Integer id;
    private String login;
    private Status status;
}
