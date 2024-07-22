package com.example.payments.repository;


import com.example.payments.entity.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;


public interface BankAccountRepository extends JpaRepository<BankAccount, Integer> {
    BankAccount getBankAccountById(Integer id);
}
