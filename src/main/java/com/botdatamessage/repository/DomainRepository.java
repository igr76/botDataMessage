package com.botdatamessage.repository;

import com.botdatamessage.model.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Репозиторий доменов */
@Repository
public interface DomainRepository extends JpaRepository<Domain, String> {
}
