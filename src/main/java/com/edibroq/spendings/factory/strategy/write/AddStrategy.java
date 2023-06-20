package com.edibroq.spendings.factory.strategy.write;

import com.edibroq.spendings.builder.ButtonsBuilder;
import com.edibroq.spendings.builder.MessageBuilder;
import com.edibroq.spendings.factory.Message;
import com.edibroq.spendings.factory.MessageInfo;
import com.edibroq.spendings.factory.SendMessageStrategy;
import com.edibroq.spendings.service.spending.SpendingService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

/**
 * Действие, выполняемое при переходе в меню добавление продукта
 */
@Component
public class AddStrategy implements SendMessageStrategy {

    private final SpendingService spendingService;

    private static final String ADD_MENU_OPENED = "Product menu opened, enter the name of the product and it's price";

    public AddStrategy(SpendingService spendingService) {
        this.spendingService = spendingService;
    }

    @Override
    public SendMessage execute(MessageInfo info) {
        var messageMarkup = ButtonsBuilder.newBuilder().row(List.of(
                "product"
        )).withBackButton().build();

        return MessageBuilder.newBuilder()
                .defaultText(info.getId(), ADD_MENU_OPENED)
                .markup(messageMarkup)
                .build();
    }

    @Override
    public Message getType() {
        return Message.ADD;
    }
}
