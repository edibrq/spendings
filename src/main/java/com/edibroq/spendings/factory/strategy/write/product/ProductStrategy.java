package com.edibroq.spendings.factory.strategy.write.product;

import com.edibroq.spendings.builder.MessageBuilder;
import com.edibroq.spendings.factory.Message;
import com.edibroq.spendings.factory.MessageInfo;
import com.edibroq.spendings.factory.SendMessageStrategy;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

@Component
public class ProductStrategy implements SendMessageStrategy {

    private static final String ADD_PRODUCT_MESSAGE = "Enter the name of the product";

    @Override
    public SendMessage execute(MessageInfo info) {
        return MessageBuilder.newBuilder()
                .defaultText(info.getId(), ADD_PRODUCT_MESSAGE)
                .build();
    }

    @Override
    public Message getType() {
        return Message.PRODUCT;
    }
}
