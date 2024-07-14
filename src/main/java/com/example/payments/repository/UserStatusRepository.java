package com.example.payments.repository;

import com.example.payments.entity.UserStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserStatusRepository extends CrudRepository<UserStatus, Integer> {
     UserStatus getUserStatusById(Integer id);
}
