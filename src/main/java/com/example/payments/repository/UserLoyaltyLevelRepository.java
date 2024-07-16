package com.example.payments.repository;


import com.example.payments.entity.UserLoyaltyLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserLoyaltyLevelRepository extends JpaRepository<UserLoyaltyLevel, Integer> {

    UserLoyaltyLevel getUserLoyaltyLevelById(Integer id);
}
