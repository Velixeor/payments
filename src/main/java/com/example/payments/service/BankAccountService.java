package com.example.payments.service;


import com.example.payments.dto.BankAccountDTO;
import com.example.payments.entity.BankAccount;
import com.example.payments.entity.Status;
import com.example.payments.repository.BankAccountRepository;
import com.example.payments.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;


@Slf4j
@Service
public class BankAccountService {
    private final BankAccountRepository bankAccountRepository;
    private final UserRepository userRepository;
    @Value("${currency:RUB}")
    private String currency;

    public BankAccountService(BankAccountRepository bankAccountRepository, UserRepository userRepository
    ) {
        this.bankAccountRepository = bankAccountRepository;
        this.userRepository = userRepository;

    }

    @Transactional
    public BankAccountDTO createBankAccount(BankAccountDTO bankAccountDTO) {
        BankAccount bankAccount = new BankAccount();
        TransferringDataInBankAccountFromBankAccountDTO(bankAccountDTO, bankAccount);
        BankAccount bankAccountResult = bankAccountRepository.save(bankAccount);
        return TransferringDataInBankAccountDTOFromBankAccount(bankAccountResult);
    }

    public BankAccountDTO updateBankAccountById(BankAccountDTO bankAccountDTO) {
        BankAccount bankAccount = bankAccountRepository.getBankAccountById(bankAccountDTO.getId());
        TransferringDataInBankAccountFromBankAccountDTO(bankAccountDTO, bankAccount);
        BankAccount bankAccountResult = bankAccountRepository.save(bankAccount);
        return TransferringDataInBankAccountDTOFromBankAccount(bankAccountResult);
    }

    private void TransferringDataInBankAccountFromBankAccountDTO(BankAccountDTO bankAccountDTO,
                                                                 BankAccount bankAccount) {
        bankAccount.setBalance(bankAccountDTO.getBalance());
        bankAccount.setCode(bankAccountDTO.getCode());
        bankAccount.setId(bankAccountDTO.getId());
        bankAccount.setDateCreate(bankAccountDTO.getDateCreate());
        bankAccount.setStatus(bankAccountDTO.getStatus());
        bankAccount.setUser(userRepository.getUserById(bankAccountDTO.getUserID()));
        bankAccount.setCurrency(bankAccountDTO.getCurrency());

    }

    public BankAccountDTO createDefaultBankAccount(Integer userID) {
        BankAccount bankAccount = new BankAccount();
        bankAccount.setBalance(3000);
        bankAccount.setUser(userRepository.getUserById(userID));
        bankAccount.setDateCreate(ZonedDateTime.now());
        bankAccount.setCurrency(currency);
        bankAccount.setStatus(Status.ACTIVE);
        bankAccount.setCode("default " + userID);
        BankAccount bankAccountResult = bankAccountRepository.save(bankAccount);
        return TransferringDataInBankAccountDTOFromBankAccount(bankAccountResult);
    }

    private BankAccountDTO TransferringDataInBankAccountDTOFromBankAccount(BankAccount bankAccount) {
        BankAccountDTO bankAccountDTO = new BankAccountDTO();
        bankAccountDTO.setBalance(bankAccount.getBalance());
        bankAccountDTO.setCode(bankAccount.getCode());
        bankAccountDTO.setId(bankAccount.getId());
        bankAccountDTO.setDateCreate(bankAccount.getDateCreate());
        bankAccountDTO.setStatus(bankAccount.getStatus());
        bankAccountDTO.setUserID(bankAccount.getUser().getId());
        bankAccountDTO.setCurrency(bankAccount.getCurrency());
        return bankAccountDTO;

    }

}