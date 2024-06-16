package ru.gb.springdemo.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс, описывающий читателя
 */
@Schema(name = "Читатель")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Reader {

    @Schema(name = "Идентификатор")
    private Long id;

    @Schema(name = "Имя читателя")
    private String name;

}
