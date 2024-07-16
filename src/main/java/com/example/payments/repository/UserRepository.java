package com.example.payments.repository;


import com.example.payments.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User getUserById(Integer id);

    List<User> findByLoginOrMailOrNumberPhone(String login, String mail, String numberPhone);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.login = :login OR u.mail = :mail OR u.numberPhone = :numberPhone")
    Boolean existsUserByLoginAndAndMailAndNumberPhone(String login, String numberPhone, String mail);


}
