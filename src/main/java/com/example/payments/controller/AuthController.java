package com.example.payments.controller;


import com.example.payments.dto.UserDTO;
import com.example.payments.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/token")
    public ResponseEntity<Map<String, Object>> getToken(@RequestParam("login") String login,
                                                        @RequestParam("password") String password) {
        try {
            Map<String, Object> response = authService.authenticateAndGenerateToken(login,password);
            return ResponseEntity.ok(response);
        } catch (AuthenticationException ex) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(Map.of("error", "Invalid credentials"));
        }
    }

    @PostMapping("/login")
    public void login(
            @RequestParam("login") String login,
            @RequestParam("password") String password) {
        authService.loginUser(login, password);
    }

    @PostMapping("/logout")
    public void logout() {
        authService.logout();
    }

    @GetMapping("/current")
    public UserDTO getCurrentUser() {
        return authService.getCallerUser()
                .map(user -> UserDTO.from(user, true))
                .orElseGet(UserDTO::new);
    }
}
