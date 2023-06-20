package com.edibroq.spendings.model;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class Price {

    private static final String DECIMAL_LONG_REGEXP = "\\d+[.,]?\\d+";

    private final BigDecimal result;

    private boolean isValid = false;

    public Price(boolean isValid, BigDecimal result) {
        this.result = result;
        this.isValid = isValid;
    }

    public Price(String text) {
        if (text.matches(DECIMAL_LONG_REGEXP) && text.length() < 12) {
            this.result = new BigDecimal(text.replace(",", "."));
        } else {
            this.result = BigDecimal.ZERO;
        }
        isValid = result.compareTo(BigDecimal.ZERO) > 0;
    }
}
