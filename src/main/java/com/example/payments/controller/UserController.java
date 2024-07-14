package com.example.payments.controller;

import com.example.payments.Service.UserService;
import com.example.payments.dto.UserDTO;


//import lombok.extern.slf4j.Slf4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Controller
@RequestMapping("/api/user")
public class UserController {
@Autowired
private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create_user")
    public ResponseEntity createUser(@RequestBody UserDTO userDTO){
    if(userService.createUser(userDTO))
        return ResponseEntity.status(201).build();
    else
        return ResponseEntity.status(409).build();


    }
    @PutMapping("/update_user")
    public ResponseEntity updateUser(@RequestBody UserDTO userDTO){
        if(userService.updateUser(userDTO))
            return ResponseEntity.status(201).build();
        else
            return ResponseEntity.status(409).build();


    }
    @DeleteMapping("/delete_user")
    public ResponseEntity deleteUser(@RequestParam Integer idUser){
        if(userService.deleteUser(idUser))
            return ResponseEntity.status(200).build();
        else
            return ResponseEntity.status(409).build();


    }
    @GetMapping("/get_user")
    public UserDTO getUser(@RequestParam Integer idUser){
        return userService.getUser(idUser);

    }


}
