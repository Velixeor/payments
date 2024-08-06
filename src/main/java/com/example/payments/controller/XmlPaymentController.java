package com.example.payments.controller;


import com.example.payments.service.PaymentXmlService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Controller
@RequestMapping("/api/v1/payment-xml")
@RequiredArgsConstructor
public class XmlPaymentController {
    private final PaymentXmlService paymentXmlService;

    @PostMapping("/create")
    public ResponseEntity<Object> processXml(@RequestBody String xml) {

        return new ResponseEntity<>(paymentXmlService.transferOfPayment(xml), HttpStatus.OK);

    }
}
