package ru.gb.springdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс, описывающий книгу
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Book {

    private Long id;
    private String name;

}
