package com.botdatamessage.repository;

import com.botdatamessage.model.Domain;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/** Репозиторий доменов */
@Repository
public interface DomainRepository extends JpaRepository<Domain, String> {
    @Query(nativeQuery = true,value = "SELECT user_chat_id, COUNT(user_chat_id) FROM domain GROUP BY user_chat_id" )
    Map<Long,Integer> findCountDomainOnChatId();
}
