package com.example.payments.controller;


import com.example.payments.dto.UserDTO;
import com.example.payments.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@Controller
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create_user")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.createUser(userDTO), HttpStatus.CREATED);
    }

    @PutMapping("/update_user")
    public ResponseEntity<UserDTO> updateUserById(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.updateUserById(userDTO), HttpStatus.OK);
    }

    @DeleteMapping("/delete_user")
    public ResponseEntity<Void> deleteUser(@RequestParam Integer idUser) {
        userService.deleteUser(idUser);
        return ResponseEntity.status(HttpStatus.OK).build();


    }

    @GetMapping("/get_user")
    public ResponseEntity<UserDTO> getUser(@RequestParam Integer idUser) {
        return new ResponseEntity<>(userService.getUser(idUser), HttpStatus.OK);

    }


}
