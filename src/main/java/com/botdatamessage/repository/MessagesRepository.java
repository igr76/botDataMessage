package com.botdatamessage.repository;

import com.botdatamessage.model.Messages;
import org.springframework.data.jpa.repository.JpaRepository;
/** Репозиторий сообщений */
public interface MessagesRepository extends JpaRepository<Messages,Integer> {
}
