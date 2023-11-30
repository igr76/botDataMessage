package com.botdatamessage.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
/** Сущность хранения доменов */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DomainUp {
    String domainname;
    int hotness;
    long price;
    int x_value;
    int yandex_tic;
    int links;
    int visitors;
    String registrar;
    int old;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    LocalDate delete_date;
    boolean rkn;
    boolean judicial;
    boolean block;
}
