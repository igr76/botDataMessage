package com.botdatamessage.service.impl;

import com.botdatamessage.backorder.BackorderUp;
import com.botdatamessage.model.Domain;
import com.botdatamessage.model.Messages;
import com.botdatamessage.model.User;
import com.botdatamessage.repository.DomainRepository;
import com.botdatamessage.repository.MessagesRepository;
import com.botdatamessage.repository.UserRepository;
import com.botdatamessage.service.MessageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
@Service
public class MessageServiceImpl implements MessageService {
    private UserRepository userRepository;
    private DomainRepository domainRepository;
    private MessagesRepository messagesRepository;
    private BackorderUp backorder;

    public MessageServiceImpl(UserRepository userRepository, DomainRepository domainRepository, MessagesRepository messagesRepository, BackorderUp backorder) {
        this.userRepository = userRepository;
        this.domainRepository = domainRepository;
        this.messagesRepository = messagesRepository;
        this.backorder = backorder;
    }

    @Override
    public void lastMessageRegister(long chatId) {
        User user = new User();
        user.setChatId(chatId);
        user.setLast_message_at(LocalDateTime.now());
        userRepository.save(user);
    }

    @Override
    public void addMessage(Long chatId, String text) {
        Messages messages = new Messages();
        messages.setChatId(chatId);
        messages.setMessage(text);
        messagesRepository.save(messages);
    }

    @Override
    public String sendReport() {
        return LocalDate.now().toString()+ "собрано: "+ domainRepository.count()+ " доменов";
    }

    @Override
    public void setDailyDomains() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        backorder.getDailyDomains();
      //  List<Domain> domainList = mapper.readValue(backorder.getDailyDomains(), Domain.class);

    }
}
