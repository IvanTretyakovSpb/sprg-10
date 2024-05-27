package ru.gb.springdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.model.Reader;
import ru.gb.springdemo.repository.IssueRepository;
import ru.gb.springdemo.repository.ReaderRepository;

import java.util.List;

@Service
public class ReaderService {
    private final ReaderRepository readerRepository;
    private final IssueRepository issueRepository;

    @Autowired
    public ReaderService(ReaderRepository readerRepository, IssueRepository issueRepository) {
        this.readerRepository = readerRepository;
        this.issueRepository = issueRepository;
    }

    public Reader getByID(Long id) {
        return readerRepository.getByID(id);
    }

    public List<Reader> getAll() {
        return readerRepository.getAll();
    }

    // Для получения списка выдач книг читателю используем метод, реализованный в репозитории выдач книг
    public List<Issue> getIssues(Long readerId) {
        return issueRepository.getIssuesByReader(readerId);
    }

    public Reader create(Reader reader) {
        return readerRepository.save(reader);
    }

    public Reader update(Long id, Reader reader) {
        return readerRepository.update(id, reader);
    }

    public void deleteById(Long id) {
        readerRepository.deleteById(id);
    }
}
