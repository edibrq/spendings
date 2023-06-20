package com.edibroq.spendings.factory.strategy;

import com.edibroq.spendings.factory.SendMessageStrategy;
import org.telegram.telegrambots.meta.api.objects.Update;

public interface StrategyFactory {
    SendMessageStrategy execute(Update update);
}
