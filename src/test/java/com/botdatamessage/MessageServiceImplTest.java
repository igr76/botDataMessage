package com.botdatamessage;

import com.botdatamessage.backorder.BackorderUp;
import com.botdatamessage.model.Messages;
import com.botdatamessage.model.User;
import com.botdatamessage.repository.DomainRepository;
import com.botdatamessage.repository.MessagesRepository;
import com.botdatamessage.repository.UserRepository;
import com.botdatamessage.service.DomainService;
import com.botdatamessage.service.impl.MessageServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
public class MessageServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private MessagesRepository messagesRepository;
    @Mock
    private BackorderUp backorder;
    @Mock
    private DomainService domainService;
    private  DomainRepository domainRepository;
    @InjectMocks
    private MessageServiceImpl messageService = new MessageServiceImpl(userRepository,messagesRepository,backorder,domainService,domainRepository);
    @Test
    void lastMessageRegisterTest() {
        User user = new User(1, LocalDateTime.of(2023, 02, 10,10,10));
        Messages messages = new Messages(3,"333",user);
        when(userRepository.save(any())).thenReturn(user);
        when(messagesRepository.save(any())).thenReturn(messages);
        messageService.lastMessageRegister(1,"333");
        verify(userRepository, times(1)).save(any());
        verify(messagesRepository, times(1)).save(any());
    }
    @Test
    void  sendReportTest() {
        List<Long> list = new ArrayList<>(Arrays.asList(1L,2L));
        when(userRepository.findAllChatId()).thenReturn(list);
        messageService.sendReport();
        verify(userRepository, times(1)).findAllChatId();
    }
    @Test
    void setDailyDomainsTest() throws IOException {
        doNothing().when(domainService).clearDomain();
        doNothing().when(backorder).setDailyDomains();
        messageService.setDailyDomains();
        verify(domainService, times(1)).clearDomain();
        verify(backorder, times(1)).setDailyDomains();
    }

}
