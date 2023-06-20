package com.edibroq.spendings.repository;

import com.edibroq.spendings.model.Item;
import com.edibroq.spendings.model.Spending;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends CrudRepository<Item, Long> {

    Item findByNameAndSpending(String name, Spending spending);

    @Transactional
    @Modifying
    @Query(value = "delete from item where exists(select id from item where price = 0) and price = 0;", nativeQuery = true)
    void backDelete();

    @Query(
            value = "select * from item where spender_id = ?1 and price = 0 order by created_at limit 1",
            nativeQuery = true
    )
    Optional<Item> findLastlyCreated(String userName);

    @Query(
            value = "select * from item where spender_id = ?1 limit 50",
            nativeQuery = true
    )
    List<Item> findItems(String userName);
}