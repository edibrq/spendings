package com.edibroq.spendings.service.spending;

import com.edibroq.spendings.model.Item;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface SpendingService {

    void start(Long userId, String userName);

    void addProduct(String spenderName, String productName);

    void addPrice(String spenderName, BigDecimal price);

    BigDecimal getAmount(Long userId, String userName);

    List<Item> geItems(String userName);

    Optional<Item> getLastItem(String userName);
}
