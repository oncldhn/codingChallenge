package com.n26.codingchallange.entity;

import com.n26.codingchallange.entity.model.Transaction;
import com.n26.codingchallange.entity.model.TransactionStatistics;
import com.n26.codingchallange.util.TransactionTestUtil;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;


public class TransactionStoreTest {

    private TransactionStore transactionStore;

    @Before
    public void prepare () {
        transactionStore = new TransactionStoreImpl();
    }

    @Test
    public void getTransactionStatistics (){
        Instant current = Instant.now();
        Transaction transaction = TransactionTestUtil.getTransaction(current.minusMillis(100L));
        transactionStore.addTransaction(transaction,current);

        TransactionStatistics statistics = transactionStore.getTransactionStatistics();

        assertThat(statistics.getCount()).isEqualTo(1L);
        assertThat(statistics.getSum()).isEqualByComparingTo(new BigDecimal(TransactionTestUtil.AMOUNT));

        transaction = TransactionTestUtil.getTransaction("14.50",current.minusMillis(50L));
        transactionStore.addTransaction(transaction,current);

        statistics = transactionStore.getTransactionStatistics();

        assertThat(statistics.getCount()).isEqualTo(2L);
        assertThat(statistics.getSum()).isEqualByComparingTo(new BigDecimal("30.00"));
        assertThat(statistics.getAvg()).isEqualByComparingTo(new BigDecimal("15"));
        assertThat(statistics.getMax()).isEqualByComparingTo(new BigDecimal("15.50"));
        assertThat(statistics.getMin()).isEqualByComparingTo(new BigDecimal("14.50"));

    }
}
