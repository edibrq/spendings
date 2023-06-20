package com.edibroq.spendings.factory.strategy.write;

import com.edibroq.spendings.builder.MessageBuilder;
import com.edibroq.spendings.factory.Message;
import com.edibroq.spendings.factory.MessageInfo;
import com.edibroq.spendings.factory.SendMessageStrategy;
import com.edibroq.spendings.service.spending.SpendingService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.math.BigDecimal;

/**
 * Действие, при запросе затраченных денег
 */
@Component
public class AmountStrategy implements SendMessageStrategy {


    private final SpendingService spendingService;

    private static final String AMOUNT_MESSAGE = "Amount is: %.3f";

    public AmountStrategy(SpendingService spendingService) {
        this.spendingService = spendingService;
    }

    @Override
    public SendMessage execute(MessageInfo info) {
        BigDecimal amount = spendingService.getAmount(info.getId(), info.getUserName());

        return MessageBuilder.newBuilder()
                .defaultText(info.getId(), String.format(AMOUNT_MESSAGE, amount))
                .build();
    }

    @Override
    public Message getType() {
        return Message.AMOUNT;
    }
}
