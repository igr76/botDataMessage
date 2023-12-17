package com.botdatamessage.backorder;

import com.botdatamessage.mapper.DomainMapper;
import com.botdatamessage.model.Domain;
import com.botdatamessage.model.DomainUp;
import com.botdatamessage.model.User;
import com.botdatamessage.repository.UserRepository;
import com.botdatamessage.service.DomainService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

/** Служба получения информации доменов */
@Slf4j
@Component
public class BackorderUp {
    private  final DomainService domainService;
    private final UserRepository userRepository;
    private final DomainMapper domainMapper;

    @Value("${applycation.domain}")
    private String daily_domains ;

    public BackorderUp(DomainService domainService, UserRepository userRepository, DomainMapper domainMapper) {
        this.domainService = domainService;
        this.userRepository = userRepository;
        this.domainMapper = domainMapper;
    }

    public void   setDailyDomains () throws ServiceException, IOException {
        log.info("getDailyDomains");
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        try {
            Domain[] domains = objectMapper.readValue(new URL(daily_domains), Domain[].class);
           // System.out.println(Arrays.toString(domains));
            log.info("закачка с сайта");
                for (Domain e : domains) {domainService.addDomain(e);}
        } catch (IOException e) {log.error(" error закачка с сайта",e);
            // handles IO exceptions
        }

    }

}
