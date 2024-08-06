package com.example.payments.controller;


import com.example.payments.dto.UserLoyaltyLevelDTO;
import com.example.payments.service.UserLoyaltyLevelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@RestController
@Controller
@RequestMapping("/api/v1/user-loyalty-level")
@RequiredArgsConstructor
public class UserLoyaltyLevelController {

    private final UserLoyaltyLevelService userLoyaltyLevelService;


    @PostMapping("/create")
    public ResponseEntity<UserLoyaltyLevelDTO> createUserLoyaltyLevel(@RequestBody UserLoyaltyLevelDTO userLoyaltyLevelDTO) {
        return new ResponseEntity<>(userLoyaltyLevelService.createUserLoyaltyLevel(userLoyaltyLevelDTO), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<UserLoyaltyLevelDTO> updateUserLoyaltyLevelById(@RequestBody UserLoyaltyLevelDTO userLoyaltyLevelDTO) {
        return new ResponseEntity<>(userLoyaltyLevelService.updateUserLoyaltyLevelById(userLoyaltyLevelDTO), HttpStatus.CREATED);
    }
}
