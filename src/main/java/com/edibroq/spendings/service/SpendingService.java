package com.edibroq.spendings.service;

import java.math.BigDecimal;

public interface SpendingService {

    void start(Long userId, String userName);

    void add(Long userId, String userName);

    BigDecimal getAmount(Long userId, String userName);
}
