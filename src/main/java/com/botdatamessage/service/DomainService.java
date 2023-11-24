package com.botdatamessage.service;

import com.botdatamessage.model.Domain;
/** Сервис доменов */
public interface DomainService {
    /** Добавить домен */
    void addDomain(Domain domain);
    /** Удалить все домены */
    void clearDomain();
}
