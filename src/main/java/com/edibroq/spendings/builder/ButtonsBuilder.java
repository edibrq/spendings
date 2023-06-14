package com.edibroq.spendings.builder;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;

public class ButtonsBuilder {

    private final ReplyKeyboardMarkup markup;

    private final List<KeyboardRow> rows;

    private ButtonsBuilder() {
        this.markup = new ReplyKeyboardMarkup();
        markup.setResizeKeyboard(true);
        this.rows = new ArrayList<>();
    }

    public static ButtonsBuilder newBuilder() {
        return new ButtonsBuilder();
    }

    public ButtonsBuilder row(List<String> values) {
        var row = new KeyboardRow();
        row.addAll(values);
        rows.add(row);
        return this;
    }


    public ButtonsBuilder withBackButton() {
        row(List.of("Back"));
        return this;
    }

    public ReplyKeyboardMarkup build() {
        markup.setKeyboard(rows);
        return markup;
    }
}
