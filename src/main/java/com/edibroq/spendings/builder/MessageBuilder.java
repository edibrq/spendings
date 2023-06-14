package com.edibroq.spendings.builder;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public class MessageBuilder {

    private final SendMessage message;

    private MessageBuilder() {
        this.message = new SendMessage();
    }

    public static MessageBuilder newBuilder() {
        return new MessageBuilder();
    }

    public MessageBuilder defaultText(Long id, String text) {
        return this.id(id).message(text);
    }

    public MessageBuilder id(Long id) {
        this.message.setChatId(String.valueOf(id));
        return this;
    }

    public MessageBuilder message(String text) {
        this.message.setText(text);
        return this;
    }

    public MessageBuilder markup(ReplyKeyboardMarkup markup) {
        this.message.setReplyMarkup(markup);
        return this;
    }

    public SendMessage build() {
        return this.message;
    }
}
