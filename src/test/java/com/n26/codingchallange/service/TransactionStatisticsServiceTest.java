package com.n26.codingchallange.service;


import com.n26.codingchallange.entity.TransactionStore;
import com.n26.codingchallange.entity.model.TransactionStatistics;
import com.n26.codingchallange.util.TransactionTestUtil;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class TransactionStatisticsServiceTest {

    private TransactionStatisticsService statisticsService;
    private TransactionStore transactionStore;

    @Before
    public void prepare () {
        transactionStore = mock(TransactionStore.class);
        statisticsService = new TransactionStatisticsServiceImpl(transactionStore);
    }

    @Test
    public void getTransactionStatistics() {
        when(transactionStore.getTransactionStatistics()).thenReturn(TransactionTestUtil.getTransactionStatistics());
        TransactionStatistics statistics = statisticsService.getStatistics();
        assertThat(statistics.getCount()).isEqualTo(1L);
        assertThat(statistics.getSum()).isEqualTo(BigDecimal.TEN);
        assertThat(statistics.getAvg()).isEqualTo(BigDecimal.TEN);
    }
}
