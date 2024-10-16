package com.example.payments.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.server.authorization.client.InMemoryRegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClient;
import org.springframework.security.oauth2.server.authorization.client.RegisteredClientRepository;
import org.springframework.security.oauth2.server.authorization.settings.AuthorizationServerSettings;

import java.util.UUID;


@Configuration
public class AuthorizationServerConfig {

    private final AuthenticationManager authenticationManager;
    private final JwtEncoder jwtEncoder;
    public AuthorizationServerConfig(AuthenticationManager authenticationManager, JwtEncoder jwtEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtEncoder = jwtEncoder;
    }
    @Bean
    public RegisteredClientRepository registeredClientRepository() {
        // Создаем клиента с необходимыми параметрами
        RegisteredClient registeredClient = RegisteredClient.withId(UUID.randomUUID().toString())
                .clientId("my-client-id") // Укажите идентификатор клиента
                .clientSecret("{noop}my-client-secret") // Секрет клиента, {noop} означает, что пароль не зашифрован
                .authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE) // Тип гранта: код авторизации
                .authorizationGrantType(AuthorizationGrantType.REFRESH_TOKEN) // Также поддерживаем обновление токена
                .redirectUri("http://localhost:8080/login/oauth2/code/my-client-redirect") // URL для редиректа после авторизации
                .scope("read") // Указываем область доступа
                .scope("write") // Можно добавить несколько областей
                .build();

        // Хранение клиента в памяти
        return new InMemoryRegisteredClientRepository(registeredClient);
    }

    @Bean
    public AuthorizationServerSettings authorizationServerSettings() {
        return AuthorizationServerSettings.builder().issuer("http://localhost:8080")
                .build();
    }
}
