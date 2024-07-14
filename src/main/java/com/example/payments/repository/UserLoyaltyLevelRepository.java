package com.example.payments.repository;


import com.example.payments.entity.UserLoyaltyLevel;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLoyaltyLevelRepository extends CrudRepository<UserLoyaltyLevel, Integer> {

    UserLoyaltyLevel getUserLoyaltyLevelById(Integer id);
}
