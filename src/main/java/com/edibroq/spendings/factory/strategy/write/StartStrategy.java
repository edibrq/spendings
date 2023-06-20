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

@Component
public class StartStrategy implements SendMessageStrategy {

    private static final String START_MESSAGE = "Chat started, you can use add or amount for further interaction";

    private final SpendingService spendingService;

    public StartStrategy(SpendingService spendingService) {
        this.spendingService = spendingService;
    }

    @Override
    public SendMessage execute(MessageInfo info) {

        spendingService.start(info.getId(), info.getUserName());

        var messageMarkup = ButtonsBuilder.newBuilder()
                .row(List.of(ADD, AMOUNT))
                .singleRow(LIST)
                .build();

        return MessageBuilder.newBuilder()
                .defaultText(info.getId(), START_MESSAGE)
                .markup(messageMarkup)
                .build();
    }

    @Override
    public Message getType() {
        return Message.START;
    }
}
