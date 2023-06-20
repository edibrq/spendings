package com.edibroq.spendings.factory.strategy.write.price;

import com.edibroq.spendings.builder.ButtonsBuilder;
import com.edibroq.spendings.builder.MessageBuilder;
import com.edibroq.spendings.factory.Message;
import com.edibroq.spendings.factory.MessageInfo;
import com.edibroq.spendings.factory.SendMessageStrategy;
import com.edibroq.spendings.model.Item;
import com.edibroq.spendings.model.Price;
import com.edibroq.spendings.repository.ItemRepository;
import com.edibroq.spendings.service.spending.SpendingService;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.List;

/**
 * Действие на добавление цены продукта
 */
@Component
public class AddPriceStrategy implements SendMessageStrategy {

    private static final String PRODUCT_ADDED = "Price %s was added for product %s";

    private static final String ERROR_TEXT = "Product price is not valid";

    private final SpendingService spendingService;

    @Override
    public SendMessage execute(MessageInfo info) {
        var price = new Price(info.getText());
        var item = spendingService.getLastItem(info.getUserName()).orElse(new Item());
        if (price.isValid()) {
            spendingService.addPrice(info.getUserName(), price.getResult());
            var messageMarkup = ButtonsBuilder.newBuilder()
                    .row(List.of(ADD, AMOUNT))
                    .singleRow(LIST)
                    .build();
            return MessageBuilder.newBuilder()
                    .markup(messageMarkup)
                    .defaultText(info.getId(), PRODUCT_ADDED.formatted(info.getText(), item.getName()))
                    .build();
        } else {
            ReplyKeyboardMarkup button = ButtonsBuilder.newBuilder()
                    .singleRow(PRICE)
                    .singleRow(BACK)
                    .build();
            return MessageBuilder.newBuilder()
                    .defaultText(info.getId(), ERROR_TEXT)
                    .markup(button)
                    .build();
        }
    }

    @Override
    public Message getType() {
        return Message.ADD_PRODUCT;
    }

    public AddPriceStrategy(SpendingService spendingService, ItemRepository itemRepository) {
        this.spendingService = spendingService;
    }
}
