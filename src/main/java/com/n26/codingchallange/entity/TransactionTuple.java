package com.n26.codingchallange.entity;

import com.n26.codingchallange.entity.model.Transaction;
import com.n26.codingchallange.entity.model.TransactionStatistics;
import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;

@Data
public class TransactionTuple {
    private TransactionStatistics transactionStatistics = new TransactionStatistics();
    private Instant lastTransactionTime = Instant.now();
    private final Object lock = new Object();

    public void addToTuple(Transaction transaction,Instant currentTime) {
        synchronized (lock) {
            if(currentTime.minusSeconds(60L).isAfter(lastTransactionTime)){
                    clear();
            }
            lastTransactionTime = transaction.getTransactionTime();
            transactionStatistics.setCount(transactionStatistics.getCount()+1);
            transactionStatistics.setSum(transactionStatistics.getSum().add(transaction.getAmount()));
            transactionStatistics.setAvg(transactionStatistics.getSum().divide(BigDecimal.valueOf(transactionStatistics.getCount()),2, RoundingMode.HALF_UP));
            transactionStatistics.setMax(transactionStatistics.getMax().compareTo(transaction.getAmount())<0 ? transaction.getAmount() : transactionStatistics.getMax());
            transactionStatistics.setMin(transactionStatistics.getMin().compareTo(transaction.getAmount())>0 ? transaction.getAmount() : transactionStatistics.getMin());
        }
    }

    public void clear() {
        this.transactionStatistics.clearValues();
    }
}
