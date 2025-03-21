package com.example.bank.fees.port.adapter.controller.fees;

import org.jmolecules.architecture.hexagonal.Adapter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.bank.fees.application.fees.TransactionFeesApplicationService;
import com.example.bank.fees.application.fees.CalculateTransactionFeesCommand;
import com.example.bank.fees.domain.model.fees.TransactionFees;

@RestController
@RequestMapping("/api/v1/fees")
@Adapter
public class FeesController {
    @Autowired
    private TransactionFeesApplicationService transactionFeesApplicationService;

    @PostMapping("/transaction")
    public TransactionFeesResponse calculateTransactionFees(@RequestBody TransactionFeesRequest request) {
        TransactionFees transactionFees = this.transactionFeesApplicationService.calculateTransactionFees(
            new CalculateTransactionFeesCommand(
                request.getTransactionType(),
                request.getAmount(), 
                request.getCurrencyCode()));
                
        return new TransactionFeesResponse(
            transactionFees.getFeesStatus().toString(), 
            transactionFees.getFees().getAmount(),
            transactionFees.getFees().getCurrencyCode().toString());
    }
}