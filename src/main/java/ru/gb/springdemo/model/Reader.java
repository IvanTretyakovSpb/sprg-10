package ru.gb.springdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Класс, описывающий читателя
 */
@Data
@AllArgsConstructor
public class Reader {

    private Long id;
    private String name;

}
