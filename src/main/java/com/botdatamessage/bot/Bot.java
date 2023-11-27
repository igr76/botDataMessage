package com.botdatamessage.bot;

import com.botdatamessage.config.BotConfig;
import com.botdatamessage.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;

/** Управление командами бота */
@Slf4j
@Component
public class Bot extends TelegramLongPollingBot {
    final BotConfig config;
    private final MessageService messageService;

    private static final String START = "/start";
    private static final String HELP = "/help";

    public Bot(BotConfig config, MessageService messageService) {
        this.config = config;
        this.messageService = messageService;
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }
        String messageTextAfter = update.getMessage().getText();
        String[] textArray = messageTextAfter.split(" ");
        String message = textArray[0];
        var chatId = update.getMessage().getChatId();
        lastMessageRegister( chatId,messageTextAfter);
        switch (message) {
            case START -> {
                String userName = update.getMessage().getChat().getUserName();
                startCommand(chatId, userName);
            }
            case HELP -> helpCommand(chatId);
            default -> unknownCommand(chatId);
        }
    }

    private void lastMessageRegister(Long chatId,String text) {
        messageService.lastMessageRegister(chatId,text);
    }


    private void startCommand(Long chatId, String userName) {
        var text = """
                Добро пожаловать в бот
                
         
                
                Дополнительные команды:
                /help - получение справки
                """;
        var formattedText = String.format(text, userName);
        sendMessage(chatId, formattedText);
    }


    private void helpCommand(Long chatId) {
        var text = """
                Справочная информация по боту
                
               
                """;
        sendMessage(chatId, text);
    }

    private void unknownCommand(Long chatId) {
        var text = "Не удалось распознать команду!";
        sendMessage(chatId, text);
    }
    private void sendMessage(Long chatId, String text) {
        var chatIdStr = String.valueOf(chatId);
        var sendMessage = new SendMessage(chatIdStr, text);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения", e);
        }
        lastMessageRegister( chatId,text);
    }
    // Суточный отчет
    @Scheduled(cron = "0 0 4 * * *")
    private void sendReport() {
        log.info("sendReport");
        messageService.sendReport().entrySet().stream()
                .forEach(e -> sendMessage(e.getKey(),e.getValue()));

    }
    // Загрузка доменов с сайта
    @Scheduled(cron = "0 0 4 * * *")
    private void setBackorderRu() throws IOException {
        log.info("setBackorderRu");
        messageService.setDailyDomains();
    }
}
