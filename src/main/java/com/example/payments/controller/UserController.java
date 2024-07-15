package com.example.payments.controller;

import com.example.payments.service.UserService;
import com.example.payments.dto.UserDTO;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


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
    public ResponseEntity<Void> createUser(@RequestBody UserDTO userDTO) {
        userService.createUser(userDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/update_user")
    public ResponseEntity<Void> updateUser(@RequestBody UserDTO userDTO) {
       userService.updateUser(userDTO);
       return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/delete_user")
    public ResponseEntity<Void> deleteUser(@RequestParam Integer idUser) {
        userService.deleteUser(idUser);
        return ResponseEntity.status(HttpStatus.OK).build();


    }

    @GetMapping("/get_user")
    public ResponseEntity<UserDTO> getUser(@RequestParam Integer idUser) {
        return new ResponseEntity<>(userService.getUser(idUser),HttpStatus.OK) ;

    }


}
