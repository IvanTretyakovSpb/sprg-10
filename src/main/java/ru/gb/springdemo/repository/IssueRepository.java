package ru.gb.springdemo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.gb.springdemo.entity.IssueEntity;

import java.time.LocalDate;
import java.util.List;

/**
 * Репозиторий для взаимодействия с хранилищем фактов выдач книг в базе данных Н2
 */
public interface IssueRepository extends JpaRepository<IssueEntity, Long> {
    List<IssueEntity> findByReaderIdAndReturnedAt(Long readerId, LocalDate returnedAt);
}
