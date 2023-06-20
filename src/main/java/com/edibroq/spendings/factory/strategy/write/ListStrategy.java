package com.edibroq.spendings.factory.strategy.write;

import com.edibroq.spendings.builder.MessageBuilder;
import com.edibroq.spendings.factory.Message;
import com.edibroq.spendings.factory.MessageInfo;
import com.edibroq.spendings.factory.SendMessageStrategy;
import com.edibroq.spendings.model.Item;
import com.edibroq.spendings.service.spending.SpendingService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ListStrategy implements SendMessageStrategy {

    private final SpendingService spendingService;

    public ListStrategy(SpendingService spendingService) {
        this.spendingService = spendingService;
    }

    @Override
    public SendMessage execute(MessageInfo info) {
        List<Item> items = spendingService.geItems(info.getUserName());
        String result;
        if (items.size() > 0) {
            result = "Products:\n";
            result += items.stream()
                    .map(Item::toString)
                    .collect(Collectors.joining("\n"));
        } else {
            result = "No items yet";
        }
        return MessageBuilder.newBuilder()
                .id(info.getId())
                .message(result)
                .build();
    }

    @Override
    public Message getType() {
        return Message.LIST;
    }
}
