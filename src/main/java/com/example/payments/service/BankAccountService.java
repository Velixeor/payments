package com.example.payments.service;


import com.example.payments.dto.BankAccountDTO;
import com.example.payments.entity.BankAccount;
import com.example.payments.entity.Status;
import com.example.payments.repository.BankAccountRepository;
import com.example.payments.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;


@Slf4j
@Service
@RequiredArgsConstructor
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    @Value("${currency:RUB}")
    private String currency;


    @Transactional
    public BankAccountDTO createBankAccount(BankAccountDTO bankAccountDTO) {
        BankAccount bankAccount = new BankAccount();
        transferringDataInBankAccountFromBankAccountDTO(bankAccountDTO, bankAccount);
        BankAccount bankAccountResult = bankAccountRepository.save(bankAccount);
        return transferringDataInBankAccountDTOFromBankAccount(bankAccountResult);
    }

    public BankAccountDTO updateBankAccountById(BankAccountDTO bankAccountDTO) {
        BankAccount bankAccount = bankAccountRepository.getBankAccountById(bankAccountDTO.getId());
        transferringDataInBankAccountFromBankAccountDTO(bankAccountDTO, bankAccount);
        BankAccount bankAccountResult = bankAccountRepository.save(bankAccount);
        return transferringDataInBankAccountDTOFromBankAccount(bankAccountResult);
    }

    private void transferringDataInBankAccountFromBankAccountDTO(final BankAccountDTO bankAccountDTO,
                                                                 BankAccount bankAccount) {

        bankAccount.setCode(bankAccountDTO.getCode());
        bankAccount.setId(bankAccountDTO.getId());
        bankAccount.setDateCreate(bankAccountDTO.getDateCreate());
        bankAccount.setStatus(bankAccountDTO.getStatus());
        bankAccount.setUser(userRepository.getUserById(bankAccountDTO.getUserID()));
        bankAccount.setCurrency(bankAccountDTO.getCurrency());

    }

    public BankAccountDTO createDefaultBankAccount(Integer userID) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setUser(userRepository.getUserById(userID));
        bankAccount.setDateCreate(ZonedDateTime.now());
        bankAccount.setCurrency(currency);
        bankAccount.setStatus(Status.ACTIVE);
        bankAccount.setCode("default " + userID);
        BankAccount bankAccountResult = bankAccountRepository.save(bankAccount);
        return transferringDataInBankAccountDTOFromBankAccount(bankAccountResult);
    }

    private BankAccountDTO transferringDataInBankAccountDTOFromBankAccount(final BankAccount bankAccount) {
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        bankAccountDTO.setCode(bankAccount.getCode());
        bankAccountDTO.setId(bankAccount.getId());
        bankAccountDTO.setDateCreate(bankAccount.getDateCreate());
        bankAccountDTO.setStatus(bankAccount.getStatus());
        bankAccountDTO.setUserID(bankAccount.getUser().getId());
        bankAccountDTO.setCurrency(bankAccount.getCurrency());
        return bankAccountDTO;

    }

}
