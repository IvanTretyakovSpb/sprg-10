package ru.gb.springdemo.repository;

import org.springframework.stereotype.Repository;
import ru.gb.springdemo.model.Issue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Класс, описывающий взаимодействие с хранилищем фактов выдач книг читателям, которое реализовано в виде ConcurrentHashMap
 */
@Repository
public class IssueRepository {
    private static AtomicLong count = new AtomicLong(0L);
    private final Map<Long, Issue> issues = new ConcurrentHashMap<>();


    public Issue getByID(Long id) {
        return issues.get(id);
    }

    public List<Issue> getAll() {
        return new ArrayList<>(issues.values());
    }

    /**
     * Получение списка выдач книг читателю, где книги ещё не возвращены
     *
     * @param readerId - идентификатор читателя
     * @return список выдач читателю
     */
    public List<Issue> getIssuesByReader(Long readerId) {
        return issues.values()
                .stream()
                .filter(x -> Objects.equals(x.getReaderId(), readerId))
                .filter(i -> i.getReturnedAt() == null)
                .toList();
    }

    public Issue save(Issue issue) {
        issue.setId(count.incrementAndGet());
        issues.put(issue.getId(), issue);
        return issue;
    }

    // При обновлении данных по выдаче устанавливаем текущие дату и время в поле даты сдачи книги
    public Issue update(Long id) {
        Issue updatedIssue = issues.get(id);
        if (updatedIssue != null) {
            updatedIssue.setReturnedAt(LocalDateTime.now());
        }
        return updatedIssue;
    }

    public void deleteById(Long id) {
        issues.remove(id);
    }
}