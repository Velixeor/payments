package com.example.payments.condition;


import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;


public class RabbitMqCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        String type = context.getEnvironment().getProperty("core.service.type");
        return "rabbitmq".equalsIgnoreCase(type);
    }
}
