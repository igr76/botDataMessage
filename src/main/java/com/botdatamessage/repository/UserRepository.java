package com.botdatamessage.repository;

import com.botdatamessage.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/** Репозиторий  пользователей */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    @Query("SELECT chat_id FROM users")
    List<Long> findAllChatId();
}
