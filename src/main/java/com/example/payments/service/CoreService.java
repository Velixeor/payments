package com.example.payments.service;


import com.example.payments.dto.BankAccountDTO;
import com.example.payments.dto.UserDTO;
import com.example.payments.dto.UserDTOCore;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Service
public class CoreService {
    private final BankAccountService bankAccountService;
    private final RestTemplate restTemplate;
    @Value("${URLCORE:http://localhost:8081/api/v1}")
    private String urlCore;

    public CoreService(BankAccountService bankAccountService, RestTemplate restTemplate) {
        this.bankAccountService = bankAccountService;
        this.restTemplate = restTemplate;
    }

    @Transactional
    public BankAccountDTO createBankAccountFromCore(BankAccountDTO bankAccountDTO) {
        String url = urlCore + "/bank-account/create";
        log.info("Request to core /bank-account/create: {}", bankAccountDTO);
        ResponseEntity<BankAccountDTO> response = restTemplate.postForEntity(url, bankAccountDTO, BankAccountDTO.class);
        return response.getBody();
    }

    @Transactional
    public UserDTOCore createUserFromCore(UserDTOCore userDTOCore) {
        String url = urlCore + "/user/create";
        log.info("Request to core /user/create: {}", userDTOCore);
        ResponseEntity<UserDTOCore> response = restTemplate.postForEntity(url, userDTOCore, UserDTOCore.class);
        return response.getBody();
    }

    @Transactional
    public UserDTOCore updateUserFromCore(UserDTOCore userDTOCore) {
        String url = urlCore + "/user/update";
        log.info("Request to core /user/update: {}", userDTOCore);
        HttpEntity<UserDTOCore> requestEntity = new HttpEntity<>(userDTOCore);
        ResponseEntity<UserDTOCore> response = restTemplate.exchange(url,
                HttpMethod.PUT,
                requestEntity,
                UserDTOCore.class);
        return response.getBody();
    }

    @Transactional
    public void databaseSynchronization(UserDTO userDTO) {
        log.info("Start transactional");
        BankAccountDTO bankAccountDTO = bankAccountService.createDefaultBankAccount(userDTO.getId());
        UserDTOCore userDTOCore = new UserDTOCore(userDTO.getId(), userDTO.getLogin(), userDTO.getStatus());
        createUserFromCore(userDTOCore);
        createBankAccountFromCore(bankAccountDTO);

    }
}
