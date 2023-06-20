package com.edibroq.spendings.factory.strategy;

import com.edibroq.spendings.factory.MessageInfo;
import com.edibroq.spendings.factory.SendMessageStrategy;
import com.edibroq.spendings.factory.strategy.write.price.AddPriceStrategy;
import com.edibroq.spendings.factory.strategy.write.product.AddProductStrategy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;

@Component
public class StrategyFactoryImpl implements StrategyFactory {

    private final List<SendMessageStrategy> strategies;

    private final AddPriceStrategy addPriceStrategy;

    private final AddProductStrategy addProductStrategy;

    public StrategyFactoryImpl(
            List<SendMessageStrategy> strategies,
            AddPriceStrategy addPriceStrategy,
            AddProductStrategy addProductStrategy
    ) {
        this.strategies = strategies;
        this.addPriceStrategy = addPriceStrategy;
        this.addProductStrategy = addProductStrategy;
    }


    @Override
    public SendMessageStrategy execute(Update update) {

        var messageInfo = new MessageInfo(update);
        var text = messageInfo.getText();

        if (text.matches("\\d+") || text.matches("\\d+\\.?\\d+")) {
            return addPriceStrategy;
        }

        return strategies.stream()
                .filter(s -> s.getType().toString().equals(messageInfo.getText()))
                .findFirst()
                .orElse(addProductStrategy);
    }
}
