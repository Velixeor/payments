package com.example.payments.service;


import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;


@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;

    public AuthService(AuthenticationManager authenticationManager, JwtEncoder jwtEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtEncoder = jwtEncoder;
    }

    public Map<String, Object> authenticateAndGenerateToken(String username, String password) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );


        String token = generateToken(authentication);
        long expiryTime = 3600L;
        Instant expiryAt = Instant.now().plusSeconds(expiryTime);


        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("expiresAt", expiryAt);

        return response;
    }


    private String generateToken(Authentication authentication) {
        Instant now = Instant.now();
        long expiry = 3600L;

        return jwtEncoder.encode(JwtEncoderParameters.from(
                JwsHeader.with(SignatureAlgorithm.RS256).build(),
                JwtClaimsSet.builder()
                        .issuer("payment")
                        .issuedAt(now)
                        .expiresAt(now.plusSeconds(expiry))
                        .subject(authentication.getName())
                        .claim("roles", authentication.getAuthorities().stream()
                                .map(GrantedAuthority::getAuthority).collect(Collectors.toList()))
                        .build()
        )).getTokenValue();
    }
}
