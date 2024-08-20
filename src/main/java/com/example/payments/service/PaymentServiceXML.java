package com.example.payments.service;


import com.example.paymentXSD.*;
import com.example.payments.dto.MoneyTransferDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.namespace.QName;
import java.io.StringWriter;
import java.math.BigDecimal;


@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentServiceXML {
    private final RabbitTemplate rabbitTemplate;

    public void transferOfPayment(MoneyTransferDTO moneyTransferDTO) {
        try {
            Document document = new Document();
            document.setFIToFICstmrCdtTrf(createFIToFICustomerCreditTransferV12(moneyTransferDTO));
            String xml = convertDocumentToXML(document);
            rabbitTemplate.convertSendAndReceive("XmlPayment","XmlRoutingKey", xml);

        } catch (JAXBException e) {
            log.error("Error converting Document to XML", e);
            throw new RuntimeException("Failed to convert Document to XML", e);
        } catch (DatatypeConfigurationException e) {
            log.error("Error converting Data in XML", e);
            throw new RuntimeException("Failed to convert Data in XML", e);
        }

    }

    public String convertDocumentToXML(Document document) throws JAXBException {
        JAXBContext context = JAXBContext.newInstance(Document.class);
        JAXBElement<Document> rootElement = new JAXBElement<>(
                new QName("urn:iso:std:iso:20022:tech:xsd:pacs.008.001.12", "Document"),
                Document.class,
                document
        );
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
        StringWriter writer = new StringWriter();
        marshaller.marshal(rootElement, writer);
        return writer.toString();
    }

    public FIToFICustomerCreditTransferV12 createFIToFICustomerCreditTransferV12(MoneyTransferDTO moneyTransferDTO) throws DatatypeConfigurationException {
        FIToFICustomerCreditTransferV12 fiToFICustomerCreditTransferV12 = new FIToFICustomerCreditTransferV12();
        fiToFICustomerCreditTransferV12.setGrpHdr(createGroupHeader113(moneyTransferDTO));
      /////Заполнить
        return fiToFICustomerCreditTransferV12;
    }

    public GroupHeader113 createGroupHeader113(MoneyTransferDTO moneyTransferDTO) throws DatatypeConfigurationException {
        GroupHeader113 groupHeader113 = new GroupHeader113();
        groupHeader113.setMsgId("1");
        XMLGregorianCalendar xmlCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar("2024-08-08T10:30:00Z");
        groupHeader113.setCreDtTm(xmlCalendar);
        groupHeader113.setNbOfTxs("1");
        groupHeader113.setBtchBookg(true);
        groupHeader113.setSttlmInf(createSettlementInstruction15(moneyTransferDTO));
        groupHeader113.setTtlIntrBkSttlmAmt(creteActiveCurrencyAndAmount(moneyTransferDTO));
        return groupHeader113;
    }

    public ActiveCurrencyAndAmount creteActiveCurrencyAndAmount(MoneyTransferDTO moneyTransferDTO) {
        ActiveCurrencyAndAmount activeCurrencyAndAmount = new ActiveCurrencyAndAmount();
        activeCurrencyAndAmount.setCcy(moneyTransferDTO.getCurrency());
        activeCurrencyAndAmount.setValue(BigDecimal.valueOf(moneyTransferDTO.getAmount()));
        return activeCurrencyAndAmount;
    }

    public SettlementInstruction15 createSettlementInstruction15(MoneyTransferDTO moneyTransferDTO) {
        SettlementInstruction15 settlementInstruction15 = new SettlementInstruction15();
        settlementInstruction15.setSttlmMtd(SettlementMethod1Code.COVE);
        CashAccount40 cashAccount40To = new CashAccount40();
        CashAccount40 cashAccount40From = new CashAccount40();
        AccountIdentification4Choice accountIdentification4ChoiceTo = new AccountIdentification4Choice();
        accountIdentification4ChoiceTo.setIBAN(String.valueOf(moneyTransferDTO.getTo()));
        AccountIdentification4Choice accountIdentification4ChoiceFrom = new AccountIdentification4Choice();
        accountIdentification4ChoiceFrom.setIBAN(String.valueOf(moneyTransferDTO.getFrom()));
        cashAccount40To.setId(accountIdentification4ChoiceTo);
        cashAccount40From.setId(accountIdentification4ChoiceFrom);
        settlementInstruction15.setSttlmAcct(cashAccount40To);
        settlementInstruction15.setThrdRmbrsmntAgtAcct(cashAccount40From);
        return settlementInstruction15;
    }


}
