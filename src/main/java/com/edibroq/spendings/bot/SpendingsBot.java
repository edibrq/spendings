package com.edibroq.spendings.bot;

import com.edibroq.spendings.model.Spending;
import com.edibroq.spendings.service.SpendingService;
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
import java.util.Optional;

@Component
public class SpendingsBot extends TelegramLongPollingBot {

    private final String botName;

    private final SpendingService spendingService;

    @SneakyThrows
    public SpendingsBot(
            @Value("${bot.token}") String botToken,
            @Value("${bot.name}") String botName,
            SpendingService spendingService) {
        super(botToken);
        this.botName = botName;
        this.spendingService = spendingService;

        var commands = List.of(
                new BotCommand("/start", "start using bot"),
                new BotCommand("/add", "add a purchase"),
                new BotCommand("/amount", "total amount of spending's")
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
                sendMessage(id, "Chat started, you cat use /add or /amount for further interaction");
            }
            case "/add" -> {
                spendingService.add(id, userName);
                sendMessage(id, "Amount of 10 is added");
            }
            case "/amount" -> {
                BigDecimal amount = spendingService.getAmount(id, userName);
                sendMessage(id, "amount is: " + amount);

            }
            default -> sendMessage(id, "Command is not supported yet");
        }
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    private void sendMessage(Long chatId, String message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message);
        sendMessage.setChatId(chatId);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

}
