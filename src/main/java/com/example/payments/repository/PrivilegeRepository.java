package com.example.payments.repository;

import com.example.payments.entity.Privilege;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PrivilegeRepository extends CrudRepository<Privilege, Integer>  {
    Privilege getBonusById(Integer id);
}
