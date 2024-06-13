package ru.gb.springdemo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс, описывающий читателя
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reader {

    private Long id;
    private String name;

}
