package com.example.payments;


import com.example.payments.condition.RabbitMqCondition;
import com.example.payments.condition.RestCondition;
import com.example.payments.service.BankAccountService;
import com.example.payments.service.CoreServiceSynchronization;
import com.example.payments.service.CoreServiceImplRabbitMq;
import com.example.payments.service.CoreServiceImplRest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.web.client.RestTemplate;


@Configuration
public class CoreServiceConfig {

    @Bean
    @Primary
    @Conditional(RabbitMqCondition.class)
    public CoreServiceSynchronization coreServiceRabbitMq(RabbitTemplate rabbitTemplate, BankAccountService bankAccountService) {
        return new CoreServiceImplRabbitMq(rabbitTemplate, bankAccountService);
    }

    @Bean
    @Primary
    @Conditional(RestCondition.class)
    public CoreServiceSynchronization coreServiceRest(BankAccountService bankAccountService, RestTemplate restTemplate) {
        return new CoreServiceImplRest(bankAccountService, restTemplate);
    }
}
