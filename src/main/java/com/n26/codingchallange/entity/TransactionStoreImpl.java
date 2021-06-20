package com.n26.codingchallange.entity;

import com.n26.codingchallange.entity.model.Transaction;
import com.n26.codingchallange.entity.model.TransactionStatistics;
import com.n26.codingchallange.util.BigDecimalUtil;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.Objects;


@Component
public class TransactionStoreImpl  implements TransactionStore {

    //transactions in a second is stored in a tuple
    private TransactionTuple [] transactionTuples = new TransactionTuple[60];

    @Override
    public void addTransaction(Transaction transaction, Instant currentTime) {
        int transactionIndex = getTransactionTupleIndex(transaction.getTransactionTime());
        TransactionTuple tuple = transactionTuples[transactionIndex];
        if(tuple == null) {
            tuple = new TransactionTuple();
            transactionTuples[transactionIndex] = tuple;
        }
        tuple.addToTuple(transaction,currentTime);
    }

    private int getTransactionTupleIndex(Instant transactionTime) {
        //get index by the second of time
        return (int)transactionTime.getLong(ChronoField.INSTANT_SECONDS) % 60;
    }

    @Override
    public void deleteTransactions() {
        for(int i = 0; i< transactionTuples.length; i++) {
            if (transactionTuples[i] != null) {
                transactionTuples[i].clear();
            }
        }
    }

    @Override
    public TransactionStatistics getTransactionStatistics() {
        //get only the transactions in the last 60 seconds
        Instant currentTimeMinus60sec = Instant.now().minusSeconds(60L);
        TransactionStatistics trxStatistics = Arrays.stream(transactionTuples).
                                filter(Objects::nonNull).
                                filter(trxTuple -> currentTimeMinus60sec.isBefore(trxTuple.getLastTransactionTime())).
                                reduce(new TransactionStatistics(),(statistics,trxTuple) ->
                        TransactionStatistics.mergeStatistics(statistics,trxTuple.getTransactionStatistics()),
                        TransactionStatistics::mergeStatistics);
        //default min and max values are replaced with 0
        if(trxStatistics.getCount() == 0L) {
            trxStatistics.setMax(BigDecimalUtil.getZero());
            trxStatistics.setMin(BigDecimalUtil.getZero());
        }
        return trxStatistics;
    }
}
