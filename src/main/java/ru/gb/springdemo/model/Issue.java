package ru.gb.springdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Класс, описывающий факт выдачи книги
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Issue {
    private Long id;
    private Long bookId;
    private Long readerId;
    private LocalDate issuedAt;
    private LocalDate returnedAt;

}
