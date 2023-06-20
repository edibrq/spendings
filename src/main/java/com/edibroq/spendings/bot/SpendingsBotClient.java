package com.edibroq.spendings.bot;

import com.edibroq.spendings.factory.MessageInfo;
import com.edibroq.spendings.factory.SendMessageStrategy;
import com.edibroq.spendings.factory.strategy.StrategyFactory;
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

import java.util.List;

@Component
public class SpendingsBotClient extends TelegramLongPollingBot {

    private final String botName;

    private final StrategyFactory factory;

    @SneakyThrows
    public SpendingsBotClient(
            @Value("${bot.token}") String botToken,
            @Value("${bot.name}") String botName,
            StrategyFactory factory
    ) {
        super(botToken);
        this.botName = botName;
        this.factory = factory;

        var commands = List.of(
                new BotCommand("start", "start using bot")
        );

        execute(new SetMyCommands(commands, new BotCommandScopeDefault(), null));
    }

    @Override
    public void onUpdateReceived(Update update) {
        SendMessageStrategy strategy = factory.execute(update);
        var info = new MessageInfo(update);
        SendMessage message = strategy.execute(info);
        send(message);
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
