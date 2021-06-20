package com.n26.codingchallange.service;

import com.n26.codingchallange.exception.GenericCodingChallangeException;

public interface TransactionService {
    void executeTransaction(String amount, String time) throws GenericCodingChallangeException;

    void deleteTransactions();
}
