package com.edibroq.spendings.service.spending;

import com.edibroq.spendings.model.Item;
import com.edibroq.spendings.model.Spending;
import com.edibroq.spendings.repository.ItemRepository;
import com.edibroq.spendings.repository.SpendingsRepository;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Component
public class SpendingServiceImpl implements SpendingService {

    private static final Logger log = LoggerFactory.getLogger(SpendingServiceImpl.class);

    private final SpendingsRepository spendingsRepository;

    private final ItemRepository itemRepository;

    public SpendingServiceImpl(
            SpendingsRepository spendingsRepository,
            ItemRepository itemRepository
    ) {
        this.spendingsRepository = spendingsRepository;
        this.itemRepository = itemRepository;
    }


    @Override
    public void start(Long userId, String userName) {
        Optional<Spending> spendingOptional = spendingsRepository.findBySpender(userName);
        if (spendingOptional.isEmpty()) {
            Spending spending = new Spending(BigDecimal.ZERO, userName);
            spendingsRepository.save(spending);
        } else {
            log.error(String.format("User %s already exists.", userName));
        }
    }

    @Override
    @Transactional
    public void addProduct(String spenderName, String productName) {
        Optional<Spending> spenderOptional = spendingsRepository.findBySpender(spenderName);
        if (spenderOptional.isPresent()) {
            var spender = spenderOptional.get();
            Item item = new Item();
            item.setId(generateId());
            item.setSpending(spender);
            item.setName(productName);
            item.setCreatedAt(Instant.now());
            item.setPrice(BigDecimal.valueOf(0L));
            itemRepository.save(item);
        } else {
            log.error(String.format("there is no user with id: %s", spenderName));
        }
    }

    @Override
    @Transactional
    public void addPrice(String spenderName, BigDecimal price) {
        Optional<Spending> spenderOptional = spendingsRepository.findBySpender(spenderName);
        if (spenderOptional.isPresent()) {
            var spender = spenderOptional.get();
            var item = itemRepository.findLastlyCreated(spenderName).orElseThrow(() -> new RuntimeException("There is no such a item!"));
            BigDecimal amount = spender.getAmount();
            item.setPrice(price);
            spender.setAmount(amount.add(item.getPrice()));
        } else {
            log.error(String.format("there is no user with id: %s", spenderName));
        }
    }

    @Override
    public BigDecimal getAmount(Long userId, String userName) {
        Optional<Spending> spendingOptional = spendingsRepository.findBySpender(userName);
        if (spendingOptional.isPresent()) {
            return spendingOptional.get().getAmount();
        }
        return BigDecimal.ZERO;
    }

    @Override
    public List<Item> geItems(String userName) {
        Optional<Spending> spendingOptional = spendingsRepository.findBySpender(userName);
        if (spendingOptional.isPresent()) {
            return itemRepository.findItems(userName);
        } else {
            log.error("List of items not found");
            return Collections.emptyList();
        }
    }

    @Override
    public Optional<Item> getLastItem(String userName) {
        return itemRepository.findLastlyCreated(userName);
    }


    private Long generateId() {
        return UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
    }
}
