package com.example.payments.service;


import com.example.paymentXSD.Document;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;


@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentXmlService {
    private final RabbitTemplate rabbitTemplate;
    private final Jackson2JsonMessageConverter messageConverter;
    public Document transferOfPayment(String paymentXML) {
        try {
            JAXBContext jaxbContext = JAXBContext.newInstance(Document.class);
            Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();


            StringReader reader = new StringReader(paymentXML);
            JAXBElement<Document> jaxbElement = (JAXBElement<Document>) unmarshaller.unmarshal(reader);
            Document document = jaxbElement.getValue();
            MessageProperties messageProperties = new MessageProperties();
            Message message = messageConverter.toMessage(document, messageProperties);
            rabbitTemplate.convertSendAndReceive("XmlPayment", "XmlRoutingKey", message);
            return document;
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }
    }
}
