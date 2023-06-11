package com.edibroq.spendings.repository;

import com.edibroq.spendings.model.Spending;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SpendingsRepository extends CrudRepository<Spending, Long> {

    Optional<Spending> findBySpender(String spender);
}
