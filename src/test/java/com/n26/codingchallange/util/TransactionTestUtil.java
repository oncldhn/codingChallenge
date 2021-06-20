package com.n26.codingchallange.util;

import com.n26.codingchallange.entity.model.Transaction;
import com.n26.codingchallange.entity.model.TransactionStatistics;

import java.math.BigDecimal;
import java.time.Instant;

public class TransactionTestUtil {
    public static String AMOUNT = "15.50";

    public static String TIMESTAMP = "2021-06-19T17:40:43.604Z";
;
    public static TransactionStatistics getTransactionStatistics () {
        TransactionStatistics statistics = new TransactionStatistics();
        statistics.setCount(1L);
        statistics.setMax(BigDecimal.TEN);
        statistics.setMin(BigDecimal.TEN);
        statistics.setSum(BigDecimal.TEN);
        statistics.setAvg(BigDecimal.TEN);
        return statistics;
    }

    public static String getTransactionRequest () {
        return "{\"amount\": 15.50,\"timestamp\": \"2021-06-19T17:40:43.604Z\" }";
    }

    public static Instant getInstantByOffset(long offset) {
        return Instant.now().plusMillis(offset);
    }

    public static Transaction getTransaction(Instant transactionTime) {
        return new Transaction(new BigDecimal(AMOUNT),transactionTime);
    }

    public static Transaction getTransaction(String amount,Instant transactionTime) {
        return new Transaction(new BigDecimal(amount),transactionTime);
    }
}
