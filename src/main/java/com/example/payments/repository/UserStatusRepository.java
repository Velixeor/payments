package com.example.payments.repository;


import com.example.payments.entity.UserStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserStatusRepository extends JpaRepository<UserStatus, Integer> {
    UserStatus getUserStatusById(Integer id);
}
