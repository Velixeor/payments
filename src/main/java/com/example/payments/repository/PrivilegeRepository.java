package com.example.payments.repository;


import com.example.payments.entity.Privilege;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PrivilegeRepository extends JpaRepository<Privilege, Integer> {
    Privilege getBonusById(Integer id);
}
