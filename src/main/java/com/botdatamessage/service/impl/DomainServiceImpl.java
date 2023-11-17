package com.botdatamessage.service.impl;

import com.botdatamessage.model.Domain;
import com.botdatamessage.repository.DomainRepository;
import com.botdatamessage.service.DomainService;

public class DomainServiceImpl implements DomainService {
    private DomainRepository domainRepository;
    @Override
    public void addDomain(Domain domain) {
        domainRepository.save(domain);
    }
}
