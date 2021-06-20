package com.n26.codingchallange.service;

import com.n26.codingchallange.entity.TransactionStore;
import com.n26.codingchallange.entity.model.Transaction;
import com.n26.codingchallange.exception.GenericCodingChallangeException;
import com.n26.codingchallange.exception.JSONParseException;
import com.n26.codingchallange.exception.TransactionDateIsInFutureException;
import com.n26.codingchallange.exception.TransactionExpiredException;
import com.n26.codingchallange.util.TransactionTestUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.time.Instant;

import static org.mockito.Mockito.*;

public class TransactionServiceTest {

    private TransactionService transactionService;
    private TransactionStore transactionStore;

    @Before
    public void prepare () {
        transactionStore = mock(TransactionStore.class);
        transactionService = new TransactionServiceImpl(transactionStore);
    }

    @Test
    public void deleteTransactions () {
        transactionService.deleteTransactions();

        verify(transactionStore).deleteTransactions();
    }

    @Test(expected = TransactionExpiredException.class)
    public void executeTransaction_TransactionIsOld () throws GenericCodingChallangeException {
        transactionService.executeTransaction(TransactionTestUtil.AMOUNT,TransactionTestUtil.TIMESTAMP);
    }

    @Test(expected = TransactionDateIsInFutureException.class)
    public void executeTransaction_TransactionTimeIsInFuture () throws GenericCodingChallangeException {
        transactionService.executeTransaction(TransactionTestUtil.AMOUNT,TransactionTestUtil.getInstantByOffset(1000).toString());
    }

    @Test(expected = JSONParseException.class)
    public void executeTransaction_AmountParseError () throws GenericCodingChallangeException {
        transactionService.executeTransaction("A",TransactionTestUtil.getInstantByOffset(1000).toString());
    }

    @Test(expected = JSONParseException.class)
    public void executeTransaction_TransactionTimeParseError () throws GenericCodingChallangeException {
        transactionService.executeTransaction(TransactionTestUtil.AMOUNT,"SDS");
    }

    @Test
    public void executeTransaction_Success () throws GenericCodingChallangeException {
        Instant transactionTime = TransactionTestUtil.getInstantByOffset(0);
        Transaction transaction = TransactionTestUtil.getTransaction(transactionTime);
        transactionService.executeTransaction(TransactionTestUtil.AMOUNT,transactionTime.toString());

        verify(transactionStore).addTransaction(Mockito.eq(transaction), Mockito.any());
    }
}
