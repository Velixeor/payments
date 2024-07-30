package com.example.payments.service;


import com.example.payments.dto.BankAccountDTO;
import com.example.payments.dto.CoreSynchronizationDTO;
import com.example.payments.dto.UserCoreDTO;
import com.example.payments.dto.UserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CoreServiceImplRabbitMq implements CoreService {
    private final RabbitTemplate rabbitTemplate;
    private final BankAccountService bankAccountService;
    @Autowired
    private Jackson2JsonMessageConverter messageConverter;

    public CoreServiceImplRabbitMq(RabbitTemplate rabbitTemplate, BankAccountService bankAccountService) {
        this.rabbitTemplate = rabbitTemplate;
        this.bankAccountService = bankAccountService;
    }

    @Override
    public BankAccountDTO createCoreBankAccount(BankAccountDTO bankAccountDTO) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("messageType", "createBankAccount");
        Message message = rabbitTemplate.getMessageConverter().toMessage(bankAccountDTO, messageProperties);
        BankAccountDTO response = (BankAccountDTO) rabbitTemplate.convertSendAndReceive("PaymentExchange", "PaymentRoutingKey", message);
        if (response == null) {
            throw new RuntimeException("Failed to create Bank Account ");
        }

        return response;
    }

    @Override
    public UserCoreDTO createCoreUser(UserCoreDTO userCoreDTO) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("messageType", "createUser");
        Message message = rabbitTemplate.getMessageConverter().toMessage(userCoreDTO, messageProperties);
        UserCoreDTO response = (UserCoreDTO) rabbitTemplate.convertSendAndReceive("PaymentExchange", "PaymentRoutingKey", message);
        if (response == null) {
            throw new RuntimeException("Failed to create user");
        }

        return response;
    }

    @Override
    public UserCoreDTO updateCoreUser(UserCoreDTO userCoreDTO) {
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("messageType", "updateUser");
        Message message = rabbitTemplate.getMessageConverter().toMessage(userCoreDTO, messageProperties);
        UserCoreDTO response = (UserCoreDTO) rabbitTemplate.convertSendAndReceive("PaymentExchange", "PaymentRoutingKey", message);
        if (response == null) {
            throw new RuntimeException("Failed to create user");
        }

        return response;
    }

    @Override
    public void coreSynchronization(UserDTO userDTO) {
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
        MessageProperties messageProperties = new MessageProperties();
        messageProperties.setHeader("messageType", "synchronization");
        Message message = messageConverter.toMessage(coreSynchronizationDTO, messageProperties);
       rabbitTemplate.convertSendAndReceive("PaymentExchange", "PaymentRoutingKey", message);


    }


}
