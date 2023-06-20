package com.edibroq.spendings.factory;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

public interface SendMessageStrategy {

    String ADD = "add";

    String AMOUNT = "amount";

    String LIST = "spendings";

    String PRICE = "price";

    String BACK = "back";

    SendMessage execute(MessageInfo info);

    Message getType();
}
