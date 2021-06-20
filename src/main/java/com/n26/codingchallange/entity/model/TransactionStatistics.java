package com.n26.codingchallange.entity.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.n26.codingchallange.util.BigDecimalUtil;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Getter
@Setter
public class TransactionStatistics {
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private BigDecimal sum;
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private BigDecimal avg;
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private BigDecimal max;
    @JsonFormat(shape=JsonFormat.Shape.STRING)
    private BigDecimal min;
    private long count;

    public TransactionStatistics() {
        clearValues();
    }

    public void clearValues() {
        sum = BigDecimalUtil.getZero();
        avg = BigDecimalUtil.getZero();
        max = BigDecimalUtil.getFromLong(Long.MIN_VALUE);
        min = BigDecimalUtil.getFromLong(Long.MAX_VALUE);
        count = 0L;
    }

    public static TransactionStatistics mergeStatistics(TransactionStatistics statistic1,TransactionStatistics statistic2) {
        if(statistic2 == null) {
            return statistic1;
        }
        TransactionStatistics statistics = new TransactionStatistics();
        statistics.setMax(statistic1.getMax().compareTo(statistic2.getMax()) > 0 ? statistic1.getMax() : statistic2.getMax());
        statistics.setMin(statistic1.getMin().compareTo(statistic2.getMin()) < 0 ? statistic1.getMin() : statistic2.getMin());
        statistics.setSum(statistic1.getSum().add(statistic2.getSum()));
        statistics.setCount(statistic1.getCount() + statistic2.getCount());
        if (statistics.getCount() > 0) {
          statistics.setAvg(
              statistics
                  .getSum()
                  .divide(BigDecimal.valueOf(statistics.getCount()), 2, RoundingMode.HALF_UP));
        }
        return statistics;
    }
}
