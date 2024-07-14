package com.example.payments.repository;

import com.example.payments.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface  UserRepository extends CrudRepository<User, Integer> {
    @Query("select u from User u")
    List<User> getAll();

    User getUserById(Integer id);
    @Query("select u from User u where u.login=:login OR u.mail = :mail OR u.numberPhone = :numberPhone ")
    List<User> getUsersByLoginOrMailOrNumberPhone(String login,String mail,String numberPhone);

    @Query("SELECT COUNT(u) > 0 FROM User u WHERE u.login = :login OR u.mail = :mail OR u.numberPhone = :numberPhone")
    Boolean existsUserByLoginAndAndMailAndNumberPhone(String login,String numberPhone,String mail);



}
