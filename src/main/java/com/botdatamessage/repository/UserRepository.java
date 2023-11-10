package com.botdatamessage.repository;

import com.botdatamessage.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/** Репозиторий счетов пользователя */
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
}
