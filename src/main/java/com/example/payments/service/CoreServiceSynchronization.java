package com.example.payments.service;


import com.example.payments.dto.BankAccountDTO;
import com.example.payments.dto.UserCoreDTO;
import com.example.payments.dto.UserDTO;


public interface CoreServiceSynchronization {
    BankAccountDTO createCoreBankAccount(BankAccountDTO bankAccountDTO);
    UserCoreDTO createCoreUser(UserCoreDTO userCoreDTO);
    UserCoreDTO updateCoreUser(UserCoreDTO userCoreDTO);
    void coreSynchronization(UserDTO userDTO);
}
