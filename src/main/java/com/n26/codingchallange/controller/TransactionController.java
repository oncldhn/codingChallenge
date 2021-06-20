package com.n26.codingchallange.controller;

import com.n26.codingchallange.controller.request.RequestTransaction;
import com.n26.codingchallange.exception.GenericCodingChallangeException;
import com.n26.codingchallange.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TransactionController {
    private final TransactionService transactionService;

    @Autowired
    public TransactionController (TransactionService transactionService) {
        this.transactionService =transactionService;
    }

    @PostMapping("/transactions")
    public ResponseEntity<Void> executeTransaction(@RequestBody RequestTransaction request) throws GenericCodingChallangeException {
        transactionService.executeTransaction(request.getAmount(),request.getTimestamp());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping ("/transactions")
    public ResponseEntity<Void> deleteTransactions() {
        transactionService.deleteTransactions();
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
