package com.example.payments.service;

import com.example.payments.entity.User;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jose.jws.SignatureAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    private final HttpSession session;
    private final HttpServletRequest request;

    public AuthService(AuthenticationManager authenticationManager, JwtEncoder jwtEncoder, HttpSession session,
                       HttpServletRequest request) {
        this.authenticationManager = authenticationManager;
        this.jwtEncoder = jwtEncoder;
        this.session = session;
        this.request = request;
    }

    public void loginUser(String login, String password) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(login, password);
        Authentication authenticated = authenticationManager.authenticate(authentication);
        context.setAuthentication(authenticated);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, context);
    }

    public Optional<User> getCallerUser() {
        if (authenticated()) {
            return Optional.of((User) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        }
        return Optional.empty();
    }

    public boolean authenticated() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.isAuthenticated() && (authentication.getPrincipal() instanceof User);
    }

    public void logout() {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    public Map<String, Object> authenticateAndGenerateToken(String username, String password) {
        String token = generateToken(SecurityContextHolder.getContext().getAuthentication());
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
