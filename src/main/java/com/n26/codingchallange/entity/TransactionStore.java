package com.n26.codingchallange.entity;

import com.n26.codingchallange.entity.model.Transaction;
import com.n26.codingchallange.entity.model.TransactionStatistics;

import java.time.Instant;

public interface TransactionStore {
     void addTransaction(Transaction transaction, Instant currentTime);

     void deleteTransactions();

     TransactionStatistics getTransactionStatistics();
}
