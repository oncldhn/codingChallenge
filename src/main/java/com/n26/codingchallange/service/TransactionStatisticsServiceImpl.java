package com.n26.codingchallange.service;

import com.n26.codingchallange.entity.model.TransactionStatistics;
import com.n26.codingchallange.entity.TransactionStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionStatisticsServiceImpl implements TransactionStatisticsService {

    private final TransactionStore transactionStore;

    @Autowired
    public  TransactionStatisticsServiceImpl (TransactionStore transactionStore) {
        this.transactionStore=transactionStore;
    }

    @Override
    public TransactionStatistics getStatistics() {
        return transactionStore.getTransactionStatistics();
    }
}
