package com.example.excel.telegramm;

import com.example.excel.service.IncomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.math.BigDecimal;

@Component
@SuppressWarnings("unused")
public class TelegramBot extends TelegramLongPollingBot {

    @Value("${telegram.bot.token}")
    private String botToken;

    @Value("${telegram.bot.username}")
    private String botUsername;

    @Autowired
    private IncomeService incomeService;

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String userId = String.valueOf(update.getMessage().getFrom().getId());

            if (messageText.equals("/start")) {
                sendMessage(chatId, "Привет! Я бот для записи доходов. Просто отправь мне сумму дохода, и я сохраню её.");
            } else {
                try {
                    BigDecimal amount = new BigDecimal(messageText);
                    incomeService.saveIncome(userId, amount);
                    sendMessage(chatId, "Доход успешно записан!");
                } catch (NumberFormatException e) {
                    sendMessage(chatId, "Пожалуйста, введите сумму в формате числа.");
                }
            }
        }
    }

    private void sendMessage(long chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    @SuppressWarnings("unused")
    public String getBotToken() {
        return botToken;
    }
}
