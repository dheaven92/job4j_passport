package ru.job4j.clientapi.dto;

import lombok.Getter;

import java.time.LocalDate;

@Getter
public class PassportDto {

    private String series;

    private String number;

    private LocalDate expirationDate;
}
