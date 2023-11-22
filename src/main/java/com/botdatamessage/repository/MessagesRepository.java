package com.botdatamessage.repository;

import com.botdatamessage.model.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Репозиторий сообщений */
@Repository
public interface MessagesRepository extends JpaRepository<Messages,Integer> {
}
