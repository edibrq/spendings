package com.edibroq.spendings.factory.strategy.write;

import com.edibroq.spendings.builder.ButtonsBuilder;
import com.edibroq.spendings.builder.MessageBuilder;
import com.edibroq.spendings.factory.Message;
import com.edibroq.spendings.factory.MessageInfo;
import com.edibroq.spendings.factory.SendMessageStrategy;
import com.edibroq.spendings.repository.ItemRepository;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

/**
 * Действие, выполняемое при нажатии кнопки назад
 */
@Component
public class BackStrategy implements SendMessageStrategy {

    private static final String OPTIONS_MESSAGE = "Select the option";

    private final ItemRepository itemRepository;

    public BackStrategy(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public SendMessage execute(MessageInfo info) {
        var messageMarkup = ButtonsBuilder.newBuilder()
                .row(List.of(ADD, AMOUNT))
                .singleRow(LIST)
                .build();

        itemRepository.backDelete();

        return MessageBuilder.newBuilder()
                .defaultText(info.getId(), OPTIONS_MESSAGE)
                .markup(messageMarkup)
                .build();

    }

    @Override
    public Message getType() {
        return Message.BACK;
    }
}
