package com.edibroq.spendings.factory.strategy.write.price;

import com.edibroq.spendings.builder.MessageBuilder;
import com.edibroq.spendings.factory.Message;
import com.edibroq.spendings.factory.MessageInfo;
import com.edibroq.spendings.factory.SendMessageStrategy;
import com.edibroq.spendings.model.Item;
import com.edibroq.spendings.service.spending.SpendingService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Optional;

@Component
public class PriceStrategy implements SendMessageStrategy {

    private static final String ADD_PRICE_MESSAGE = "Enter the price for product %s";

    private static final String ADD_PRICE_MESSAGE_ERROR = "There is no such product";

    private final SpendingService spendingService;

    public PriceStrategy(SpendingService spendingService) {
        this.spendingService = spendingService;
    }

    @Override
    public SendMessage execute(MessageInfo info) {
        Optional<Item> lastItem = spendingService.getLastItem(info.getUserName());
        if (lastItem.isPresent()) {
            return MessageBuilder.newBuilder()
                    .defaultText(info.getId(), ADD_PRICE_MESSAGE.formatted(lastItem.get().getName()))
                    .build();
        } else {
            return MessageBuilder.newBuilder()
                    .defaultText(info.getId(), ADD_PRICE_MESSAGE_ERROR)
                    .build();
        }
    }

    @Override
    public Message getType() {
        return Message.PRICE;
    }
}
