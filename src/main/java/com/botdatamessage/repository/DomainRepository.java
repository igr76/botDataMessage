package com.botdatamessage.repository;

import com.botdatamessage.model.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
/** Репозиторий доменов */
public interface DomainRepository extends JpaRepository<Domain, String> {
}
