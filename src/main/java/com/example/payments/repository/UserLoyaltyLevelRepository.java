package com.example.payments.repository;


import com.example.payments.entity.UserLoyaltyLevel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserLoyaltyLevelRepository extends JpaRepository<UserLoyaltyLevel, Integer> {

    UserLoyaltyLevel getUserLoyaltyLevelById(Integer id);

    List<UserLoyaltyLevel> findUserLoyaltyLevelByCode(String code);

    @Query("SELECT COUNT(u) > 0 FROM UserLoyaltyLevel u WHERE u.code = :code")
    Boolean existsUserLoyaltyLevelByCode(String code);


}
