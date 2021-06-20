package com.n26.codingchallange.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalUtil {
    public static BigDecimal getZero() {
        return BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP);
    }

    public static BigDecimal getFromLong (long number) {
        return BigDecimal.valueOf(number).setScale(2,RoundingMode.HALF_UP);
    }


}
