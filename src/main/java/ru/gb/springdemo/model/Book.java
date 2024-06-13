package ru.gb.springdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Класс, описывающий книгу
 */
@Data
@AllArgsConstructor
public class Book {

    private Long id;
    private String name;

}
