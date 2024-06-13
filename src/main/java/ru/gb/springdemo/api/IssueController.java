package ru.gb.springdemo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.springdemo.exception.NotFoundEntityException;
import ru.gb.springdemo.exception.UserIssueLimitExceededException;
import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.service.IssueService;

import java.util.List;

@RestController
@RequestMapping("/issue")
public class IssueController {

    private final IssueService issueService;

    @Autowired
    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Issue> getByID(@PathVariable("id") Long id) {
        Issue issue = issueService.getByID(id);
        return new ResponseEntity<>(issue, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<Issue>> getAll() {
        return new ResponseEntity<>(issueService.getAll(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Issue> create(@RequestBody Issue issue) {
        issue = issueService.saveIssue(issue);
        return new ResponseEntity<>(issue, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Issue> update(@PathVariable("id") Long id) {
        Issue updatedIssue = issueService.update(id);
        return new ResponseEntity<>(updatedIssue, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        issueService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    /**
     * Метод для обработки исключений в случае некорректно указанного ID книги, читателя или выдачи
     *
     * @return ответ со статусом 404
     */
    @ExceptionHandler(NotFoundEntityException.class)
    public ResponseEntity<Void> processNotFoundEntityException() {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    /**
     * Метод для обработки исключений в случае превышения количества выдач книг для одного читателя
     *
     * @return ответ со статусом 403
     */
    @ExceptionHandler(UserIssueLimitExceededException.class)
    public ResponseEntity<Void> processUserIssueLimitExceededException() {
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}
