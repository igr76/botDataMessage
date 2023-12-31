package com.botdatamessage.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
/** Сущность хранения доменов */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Domain {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    long id;
    String domainname;
    int hotness;
    long price;
    int x_value;
    int yandex_tic;
    int links;
    int visitors;
    String registrar;
    int old;
    LocalDate delete_date;
    boolean rkn;
    boolean judicial;
    boolean block;
}
