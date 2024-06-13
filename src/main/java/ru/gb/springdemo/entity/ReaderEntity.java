package ru.gb.springdemo.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Класс, описывающий сущность читатель для работы с базой данных
 */

@Entity
@Table(name = "readers")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReaderEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

}
