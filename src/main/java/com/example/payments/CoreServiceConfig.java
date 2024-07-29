package com.example.payments;


import com.example.payments.condition.RabbitMqCondition;
import com.example.payments.condition.SynchronousCondition;
import com.example.payments.service.BankAccountService;
import com.example.payments.service.CoreService;
import com.example.payments.service.CoreServiceImplRabbitMq;
import com.example.payments.service.CoreServiceImplSynchronous;
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
    public CoreService coreServiceRabbitMq(RabbitTemplate rabbitTemplate, BankAccountService bankAccountService) {
        return new CoreServiceImplRabbitMq(rabbitTemplate, bankAccountService);
    }

    @Bean
    @Primary
    @Conditional(SynchronousCondition.class)
    public CoreService coreServiceSynchronous(BankAccountService bankAccountService, RestTemplate restTemplate) {
        return new CoreServiceImplSynchronous(bankAccountService, restTemplate);
    }
}
