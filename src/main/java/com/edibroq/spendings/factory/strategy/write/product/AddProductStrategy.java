package com.edibroq.spendings.factory.strategy.write.product;

import com.edibroq.spendings.builder.ButtonsBuilder;
import com.edibroq.spendings.builder.MessageBuilder;
import com.edibroq.spendings.factory.Message;
import com.edibroq.spendings.factory.MessageInfo;
import com.edibroq.spendings.factory.SendMessageStrategy;
import com.edibroq.spendings.service.spending.SpendingService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

/**
 * Действие на добавление продукта
 */
@Component
public class AddProductStrategy implements SendMessageStrategy {

    private static final String PRODUCT_ADDED = "Product %s was added";

    private final SpendingService service;

    public AddProductStrategy(SpendingService service) {
        this.service = service;
    }

    @Override
    public SendMessage execute(MessageInfo info) {
        service.addProduct(info.getUserName(), info.getText());
        ReplyKeyboardMarkup button = ButtonsBuilder.newBuilder()
                .singleRow("price")
                .singleRow("back")
                .build();
        return MessageBuilder.newBuilder()
                .defaultText(info.getId(), String.format(PRODUCT_ADDED, info.getText()))
                .markup(button)
                .build();
    }

    @Override
    public Message getType() {
        return Message.ADD_PRICE;
    }
}
