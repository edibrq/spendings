package com.edibroq.spendings.bot;

import com.edibroq.spendings.builder.ButtonsBuilder;
import com.edibroq.spendings.builder.MessageBuilder;
import com.edibroq.spendings.service.spending.SpendingService;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.math.BigDecimal;
import java.util.List;

@Component
public class SpendingsBotClient extends TelegramLongPollingBot {

    private final String botName;

    private final SpendingService spendingService;

    private static final String ACTIONS_MESSAGE = "Chat started, you cat use add or amount for further interaction";

    private static final String OPTIONS_MESSAGE = "Select the option";

    private static final String AMOUNT_MESSAGE = "Amount is: %.2f";

    private static final String NOT_SUPPORTED_MESSAGE = "Command is not supported yet";

    @SneakyThrows
    public SpendingsBotClient(
            @Value("${bot.token}") String botToken,
            @Value("${bot.name}") String botName,
            SpendingService spendingService) {
        super(botToken);
        this.botName = botName;
        this.spendingService = spendingService;

        var commands = List.of(
                new BotCommand("start", "start using bot"),
                new BotCommand("add", "add a purchase"),
                new BotCommand("amount", "total amount of spending's")
        );

        execute(new SetMyCommands(commands, new BotCommandScopeDefault(), null));
    }

    @Override
    public void onUpdateReceived(Update update) {
        String userName = update.getMessage().getChat().getUserName();
        String text = update.getMessage().getText();
        Long id = update.getMessage().getChat().getId();
        switch (text) {
            case "/start" -> {
                spendingService.start(id, userName);

                var messageMarkup = ButtonsBuilder.newBuilder().row(List.of(
                        "add",
                        "amount"
                )).build();

                var message = MessageBuilder.newBuilder()
                        .defaultText(id, OPTIONS_MESSAGE)
                        .markup(messageMarkup)
                        .build();
                send(message);
            }
            case "add" -> {
                var messageMarkup = ButtonsBuilder.newBuilder().row(List.of(
                        "product",
                        "price"
                )).withBackButton().build();

                var message = MessageBuilder.newBuilder()
                        .defaultText(id, OPTIONS_MESSAGE)
                        .markup(messageMarkup)
                        .build();

                send(message);
                spendingService.add(id, userName);
            }
            case "amount" -> {
                BigDecimal amount = spendingService.getAmount(id, userName);

                var message = MessageBuilder.newBuilder()
                        .defaultText(id, String.format(AMOUNT_MESSAGE, amount))
                        .build();

                send(message);
            }
            case "Back" -> {
                var messageMarkup = ButtonsBuilder.newBuilder().row(List.of(
                        "add",
                        "amount"
                )).build();

                var message = MessageBuilder.newBuilder()
                        .defaultText(id, OPTIONS_MESSAGE)
                        .markup(messageMarkup)
                        .build();

                send(message);
            }
            default -> {
                var message = MessageBuilder.newBuilder()
                        .defaultText(id, NOT_SUPPORTED_MESSAGE)
                        .build();
                send(message);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    private void send(SendMessage message) {
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

}
