package com.example.payments.controller;


import com.example.payments.dto.UserDTO;
import com.example.payments.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@Controller
@RequestMapping("/api/v1/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    @PostMapping("/create")
    public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.createUser(userDTO), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<UserDTO> updateUserById(@RequestBody UserDTO userDTO) {
        return new ResponseEntity<>(userService.updateUserById(userDTO), HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Void> deleteUser(@RequestParam Integer idUser) {
        userService.deleteUser(idUser);
        return ResponseEntity.status(HttpStatus.OK).build();


    }

    @GetMapping("")
    public ResponseEntity<UserDTO> getUser(@RequestParam Integer idUser) {
        return new ResponseEntity<>(userService.getUser(idUser), HttpStatus.OK);

    }


}
