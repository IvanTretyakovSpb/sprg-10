package ru.gb.springdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.repository.BookRepository;
import ru.gb.springdemo.repository.IssueRepository;
import ru.gb.springdemo.repository.ReaderRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class IssueService {
    private final BookRepository bookRepository;
    private final ReaderRepository readerRepository;
    private final IssueRepository issueRepository;

    // Переменная для хранения максимального количества книг для одного читателя, которое указано в файле настроек
    // Если параметр не задан - то используется значение 1.
    @Value("${application.max-allowed-books:1}")
    private int maxAllowedBooks;

    @Autowired
    public IssueService(BookRepository bookRepository, ReaderRepository readerRepository,
                        IssueRepository issueRepository) {
        this.bookRepository = bookRepository;
        this.readerRepository = readerRepository;
        this.issueRepository = issueRepository;
    }

    public Issue getByID(Long id) {
        return issueRepository.getByID(id);
    }

    public List<Issue> getAll() {
        return issueRepository.getAll();
    }

    // Перед созданием и сохранением в хранилище факта выдачи книги читателю делаем проверку валидности идентификаторов
    // книги и читателя, а также проверку на соблюдение ограничения на количество выданных читателю книг.
    public Issue saveIssue(Issue issue) {
        if (bookRepository.getByID(issue.getBookId()) == null) {
            throw new NoSuchElementException("Не найдена книга с идентификатором \"" + issue.getBookId() + "\"");
        }
        if (readerRepository.getByID(issue.getReaderId()) == null) {
            throw new NoSuchElementException("Не найден читатель с идентификатором \"" + issue.getReaderId() + "\"");
        }
        if (maxAllowedBooks <= issueRepository.getIssuesByReader(issue.getReaderId()).size()) {
            throw new RuntimeException("Читателю \"" + issue.getReaderId() + "\" отказано в выдаче по причине " +
                    "максимального количества книг на руках. Необходимо осуществить предварительный возврат книг.");
        }
        // Оформляем выдачу только после прохождения всех проверок
        return issueRepository.save(issue);
    }

    public Issue update(Long id) {
        return issueRepository.update(id);
    }

    public void deleteById(Long id) {
        issueRepository.deleteById(id);
    }
}
