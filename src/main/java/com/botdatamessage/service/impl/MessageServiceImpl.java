package com.botdatamessage.service.impl;

import com.botdatamessage.backorder.BackorderUp;
import com.botdatamessage.model.Messages;
import com.botdatamessage.model.User;
import com.botdatamessage.repository.DomainRepository;
import com.botdatamessage.repository.MessagesRepository;
import com.botdatamessage.repository.UserRepository;
import com.botdatamessage.service.DomainService;
import com.botdatamessage.service.MessageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/** Реализация сервиса сообщений */
@Slf4j
@Service
public class MessageServiceImpl implements MessageService {
    private UserRepository userRepository;
    private MessagesRepository messagesRepository;
    private BackorderUp backorder;
    private DomainService domainService;

    public MessageServiceImpl(UserRepository userRepository, MessagesRepository messagesRepository, BackorderUp backorder, DomainService domainService) {
        this.userRepository = userRepository;
        this.messagesRepository = messagesRepository;
        this.backorder = backorder;
        this.domainService = domainService;
    }
    private String N;
    @Override
    public void lastMessageRegister(long chatId, String text) {
        log.info("lastMessageRegister");
        User user = new User();
        user.setChatId(chatId);
        user.setLast_message_at(LocalDateTime.now());
        userRepository.save(user);
        Messages messages = new Messages();
        messages.setUser(user);
        messages.setMessage(text);
        messagesRepository.save(messages);
    }


    @Override
    public Map<Long, String> sendReport() {
        String text =LocalDate.now().toString()+ "собрано: "+ N+ " доменов";
        List<Long> listUsers = userRepository.findAllChatId();
        Map<Long,String> reports = new HashMap<>();
        for (Long e : listUsers) {reports.put(e,text);}
        return reports;
    }

    @Override
    public void setDailyDomains() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        domainService.clearDomain();
        backorder.setDailyDomains();

    }
}
