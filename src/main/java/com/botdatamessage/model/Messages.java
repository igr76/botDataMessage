package com.botdatamessage.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/** Сущность хранения сообщений */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "messages")
public class Messages {
    /** идентификационный номер сообщений */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    /** Текст сообщений */
    String message;
    /** Владелец сообщений */
    @ManyToOne
    User user;
}
