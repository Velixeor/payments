package com.example.payments.service;


import com.example.payments.dto.BankAccountDTO;
import com.example.payments.dto.CoreSynchronizationDTO;
import com.example.payments.dto.UserCoreDTO;
import com.example.payments.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;


@Slf4j
@Service
public class CoreServiceImplSynchronous implements CoreService{
    private final BankAccountService bankAccountService;
    private final RestTemplate restTemplate;
    @Value("${URLCORE:http://localhost:8081/api/v1}")
    private String urlCore;


    public CoreServiceImplSynchronous(BankAccountService bankAccountService, RestTemplate restTemplate) {
        this.bankAccountService = bankAccountService;
        this.restTemplate = restTemplate;

    }

    @Transactional
    public BankAccountDTO createCoreBankAccount(BankAccountDTO bankAccountDTO) {
        String url = urlCore + "/bank-account/create";
        log.info("Request to core /bank-account/create: {}", bankAccountDTO);
        ResponseEntity<BankAccountDTO> response = restTemplate.postForEntity(url, bankAccountDTO, BankAccountDTO.class);
        return response.getBody();
    }

    @Transactional
    public UserCoreDTO createCoreUser(UserCoreDTO userCoreDTO) {
        String url = urlCore + "/user/create";
        log.info("Request to core /user/create: {}", userCoreDTO);
        ResponseEntity<UserCoreDTO> response = restTemplate.postForEntity(url, userCoreDTO, UserCoreDTO.class);
        return response.getBody();
    }

    @Transactional
    public UserCoreDTO updateCoreUser(UserCoreDTO userCoreDTO) {
        String url = urlCore + "/user/update";
        log.info("Request to core /user/update: {}", userCoreDTO);
        HttpEntity<UserCoreDTO> requestEntity = new HttpEntity<>(userCoreDTO);
        ResponseEntity<UserCoreDTO> response = restTemplate.exchange(url,
                HttpMethod.PUT,
                requestEntity,
                UserCoreDTO.class);
        return response.getBody();
    }


    @Transactional
    public void coreSynchronization(UserDTO userDTO) {
        try {
            BankAccountDTO bankAccountDTO = bankAccountService.createDefaultBankAccount(userDTO.getId());
            CoreSynchronizationDTO coreSynchronizationDTO = CoreSynchronizationDTO.builder()
                    .bankAccountID(bankAccountDTO.getId())
                    .login(userDTO.getLogin())
                    .bankAccountStatus(bankAccountDTO.getStatus())
                    .bankAccountUserId(userDTO.getId())
                    .code(bankAccountDTO.getCode())
                    .currency(bankAccountDTO.getCurrency())
                    .userStatus(userDTO.getStatus())
                    .dateCreate(bankAccountDTO.getDateCreate())
                    .userId(userDTO.getId())
                    .build();

            String url = urlCore + "/synchronization/";
            log.info("Request to core /synchronization: {}", coreSynchronizationDTO);

            ResponseEntity<CoreSynchronizationDTO> response = restTemplate.postForEntity(url, coreSynchronizationDTO, CoreSynchronizationDTO.class);

            if (response.getStatusCode() != HttpStatus.OK) {
                throw new RuntimeException("Failed to synchronize with core: " + response.getStatusCode());
            }

        } catch (RestClientException e) {
            log.error("Error during synchronization with core: ", e);
            throw new RuntimeException("Failed to synchronize with core", e);
        }

        log.info("End transactional");


    }
}
