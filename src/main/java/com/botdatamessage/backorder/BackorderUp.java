package com.botdatamessage.backorder;

import com.botdatamessage.model.Domain;
import com.botdatamessage.service.DomainService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.URL;

/** Служба получения информации доменов */
@Slf4j
@Component
public class BackorderUp {
    private  final DomainService domainService;

    @Value("${https://backorder.ru/json/?order=desc&expired=1&by=hotness&page=1&items=50}")
    private String daily_domains ;

    public BackorderUp(DomainService domainService) {
        this.domainService = domainService;
    }

    public void   setDailyDomains () throws ServiceException, IOException {
        log.info("getDailyDomains");
        ObjectMapper objectMapper = new ObjectMapper();
        try {Domain[] domains = objectMapper.readValue(new URL(daily_domains), Domain[].class);
            log.info("закачка с сайта");
            for (Domain e : domains) {domainService.addDomain(e);}
        } catch (IOException e) {
            // handles IO exceptions
        }
       
       // List<Domain> list = objectMapper.readValue(stringDomain, new TypeReference<List<Domain>>() { });
       // return list;

    }

}
