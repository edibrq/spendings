package com.edibroq.spendings.service;

import com.edibroq.spendings.model.Spending;
import com.edibroq.spendings.repository.SpendingsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Optional;


@Component
public class SpendingServiceImpl implements SpendingService {

    private static final Logger log = LoggerFactory.getLogger(SpendingServiceImpl.class);

    private final SpendingsRepository repository;

    public SpendingServiceImpl(SpendingsRepository repository) {
        this.repository = repository;
    }


    @Override
    public void start(Long userId, String userName) {
        Optional<Spending> spendingOptional = repository.findBySpender(userName);
        if (spendingOptional.isEmpty()) {
            Spending spending = new Spending(BigDecimal.ZERO, userName);
            repository.save(spending);
        } else {
            log.error("there is no user with this id");
        }
    }

    @Override
    public void add(Long userId, String userName) {
        Optional<Spending> spendingOptional = repository.findBySpender(userName);
        if (spendingOptional.isPresent()) {
            Spending spending = spendingOptional.get();
            BigDecimal amount = spending.getAmount();
            BigDecimal newAmount = amount.add(BigDecimal.valueOf(10L));
            spending.setAmount(newAmount);
            repository.save(spending);
        } else {
            log.error("there is no user with this id");
        }
    }

    @Override
    public BigDecimal getAmount(Long userId, String userName) {
        Optional<Spending> spendingOptional = repository.findBySpender(userName);
        if (!spendingOptional.isEmpty()) {
            return spendingOptional.get().getAmount();
        }
        return BigDecimal.ZERO;
    }
}
