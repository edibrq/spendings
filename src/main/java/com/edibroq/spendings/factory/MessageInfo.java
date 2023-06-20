package com.edibroq.spendings.factory;

import lombok.Getter;
import org.telegram.telegrambots.meta.api.objects.Update;

@Getter
public class MessageInfo {

    private final String userName;

    private final String text;

    private final Long id;


    public MessageInfo(Update update) {
        var message = update.getMessage();
        this.userName = message.getChat().getUserName();
        this.text = message.getText();
        this.id = message.getChat().getId();
    }

}

