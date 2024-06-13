package ru.gb.springdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

/**
 * Класс, описывающий факт выдачи книги
 */
@Data
@AllArgsConstructor
public class Issue {
    private Long id;
    private Long bookId;
    private Long readerId;
    private LocalDate issuedAt;
    private LocalDate returnedAt;

}
