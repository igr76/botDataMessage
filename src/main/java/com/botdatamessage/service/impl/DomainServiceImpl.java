package com.botdatamessage.service.impl;

import com.botdatamessage.model.Domain;
import com.botdatamessage.repository.DomainRepository;
import com.botdatamessage.service.DomainService;
import org.springframework.stereotype.Service;
/** Реализация сервиса доменов */
@Service
public class DomainServiceImpl implements DomainService {
    private DomainRepository domainRepository;

    public DomainServiceImpl(DomainRepository domainRepository) {
        this.domainRepository = domainRepository;
    }

    @Override
    public void addDomain(Domain domain) {
        domainRepository.save(domain);
    }

    @Override
    public void clearDomain() {
        domainRepository.deleteAll();
    }
}
