package com.botdatamessage.service;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.io.IOException;
import java.util.Map;
/** Сервис сообщений и доменов */
public interface MessageService {
    /** Регистрация активности */
    void lastMessageRegister(long chatId);
    /** Добавить сообщение */
    void addMessage(Long chatId,String text);
    /** Суточный отчет */
    String sendReport();
    /** Запись данных с сайта */
    void setDailyDomains() throws IOException;
}
