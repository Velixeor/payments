package com.example.payments.controller;


import com.example.payments.dto.MoneyTransferDTO;
import com.example.payments.service.PaymentServiceXML;
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
public class PaymentControllerXML {
    private final PaymentServiceXML paymentServiceXML;

    @PostMapping("/create")
    public ResponseEntity<Void> processXML(@RequestBody MoneyTransferDTO moneyTransferDTO) {
        paymentServiceXML.transferOfPayment(moneyTransferDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
