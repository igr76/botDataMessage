package com.botdatamessage.bot;

import com.botdatamessage.config.BotConfig;
import com.botdatamessage.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/** Управление командами бота */
@Slf4j
@Component
public class Bot extends TelegramLongPollingBot {
    final BotConfig config;
    @Autowired
    private MessageService messageService;

    private static final String START = "/start";
    private static final String HELP = "/help";

    public Bot(BotConfig config) {
        this.config = config;
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
                try {
                    getBackorderRu();
                    log.info("start");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                startCommand(chatId, userName);
            }
            case HELP -> helpCommand(chatId);
            default -> unknownCommand(chatId);
        }
    }

    private void lastMessageRegister(Long chatId,String text) {
        messageService.lastMessageRegister(chatId);
     //   messageService.addMessage(chatId,text);
    }


    private void startCommand(Long chatId, String userName) {
        var text = """
                Добро пожаловать в бот, %s!
                
                Здесь Вы сможете узнать официальные курсы валют на сегодня, установленные ЦБ РФ.
                
                Для этого воспользуйтесь командами:
                /usd - курс доллара
                /eur - курс евро
                
                Дополнительные команды:
                /help - получение справки
                """;
        var formattedText = String.format(text, userName);
        sendMessage(chatId, formattedText);
    }


    private void helpCommand(Long chatId) {
        var text = """
                Справочная информация по боту
                
                Для получения текущих курсов валют воспользуйтесь командами:
                /usd - курс доллара
                /eur - курс евро
                /getMoney
                /setMoney пополнить счёт, после команды номер валюты и сумма 1-rub  2-usd 3-eur
                /exchange - обмен валюты меджу счетами, после команжды номер операции и сумма
                 (через олин пробел) 1:rub-usd  2:rub-eur 3:usd-rub 4:usd-eur 
                  5:eur-rub  6:eur-usd
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
        ReplyKeyboardMarkup keyboardMarkup =new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();
        KeyboardRow row= new KeyboardRow();
        row.add("/getMoney");row.add("/usd");row.add("/eur ");
        keyboardRows.add(row);
        keyboardMarkup.setKeyboard(keyboardRows);
        sendMessage.setReplyMarkup(keyboardMarkup);
        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Ошибка отправки сообщения", e);
        }
    }
    // Суточный отчет
    @Scheduled(cron = "0 0 0 * * *")
    private void sendReport() {
//        messageService.sendReport().entrySet().stream()
//                .forEach(e -> sendMessage(e.getKey(),e.getValue()));

    }
   // @Scheduled(cron = "0 0 0 * * *")
    private void getBackorderRu() throws IOException {
        log.info("getBackorderRu");
        messageService.setDailyDomains();
    }
}
