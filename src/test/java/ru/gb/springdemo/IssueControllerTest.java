package ru.gb.springdemo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.test.web.reactive.server.WebTestClient;
import ru.gb.springdemo.entity.IssueEntity;
import ru.gb.springdemo.model.Book;
import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.model.Reader;
import ru.gb.springdemo.repository.IssueRepository;
import ru.gb.springdemo.service.BookService;
import ru.gb.springdemo.service.IssueService;
import ru.gb.springdemo.service.ReaderService;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

class IssueControllerTest extends JUnitSpringBootBase {

    @Autowired
    WebTestClient webTestClient;
    @Autowired
    IssueService issueService;
    @Autowired
    BookService bookService;
    @Autowired
    ReaderService readerService;
    @Autowired
    IssueRepository issueRepository;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    static class JUnitIssue {
        private Long id;
        private Long bookId;
        private Long readerId;
        private LocalDate issuedAt;
        private LocalDate returnedAt;

        public JUnitIssue(Long bookId, Long readerId) {
            this.bookId = bookId;
            this.readerId = readerId;
        }
    }

    @Test
    void testGetByIdSuccess() {
        Book book = bookService.create(new Book("Spring in action"));
        Reader reader = readerService.create(new Reader("Ivan"));
        Issue expected = issueService.saveIssue(new Issue(book.getId(), reader.getId()));

        JUnitIssue responseBody = webTestClient.get()
                .uri("/issue/" + expected.getId())
                .exchange()
                .expectStatus().isOk()
                .expectBody(JUnitIssue.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(expected.getId(), responseBody.getId());
        Assertions.assertEquals(expected.getBookId(), responseBody.getBookId());
        Assertions.assertEquals(expected.getReaderId(), responseBody.getReaderId());
        Assertions.assertEquals(expected.getIssuedAt(), responseBody.getIssuedAt());
        Assertions.assertNull(responseBody.getReturnedAt());

    }

    @Test
    void testGetByIdNotFound() {
        webTestClient.get()
                .uri("/issue/" + Long.MAX_VALUE)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void testGetAll() {
        Book book1 = bookService.create(new Book("Java is simple"));
        Reader reader1 = readerService.create(new Reader("Nick"));
        Book book2 = bookService.create(new Book("Python is very strange"));
        Reader reader2 = readerService.create(new Reader("Bob"));
        issueRepository.saveAll(List.of(
                new IssueEntity(book1.getId(), reader1.getId(), LocalDate.now()),
                new IssueEntity(book2.getId(), reader2.getId(), LocalDate.now())
        ));
        List<Issue> expected = issueService.getAll();

        List<JUnitIssue> responseBody = webTestClient.get()
                .uri("/issue")
                .exchange()
                .expectStatus().isOk()
                .expectBody(new ParameterizedTypeReference<List<JUnitIssue>>() {
                })
                .returnResult()
                .getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(expected.size(), responseBody.size());
        for (JUnitIssue issue : responseBody) {
            boolean found = expected.stream()
                    .filter(it -> Objects.equals(it.getId(), issue.getId()))
                    .filter(it -> Objects.equals(it.getBookId(), issue.getBookId()))
                    .filter(it -> Objects.equals(it.getReaderId(), issue.getReaderId()))
                    .anyMatch(it -> Objects.equals(it.getIssuedAt(), issue.getIssuedAt()));
            Assertions.assertTrue(found);
        }
    }

    @Test
    void testCreateIssue() {
        Book book = bookService.create(new Book("Java -> Kotlin"));
        Reader reader = readerService.create(new Reader("Stepan"));
        JUnitIssue createdIssue = new JUnitIssue(book.getId(), reader.getId());

        JUnitIssue responseBody = webTestClient.post()
                .uri("/issue")
                .bodyValue(createdIssue)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(JUnitIssue.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertNotNull(responseBody.getId());
        Assertions.assertEquals(createdIssue.getBookId(), responseBody.getBookId());
        Assertions.assertEquals(createdIssue.getReaderId(), responseBody.getReaderId());
        Assertions.assertEquals(LocalDate.now(), responseBody.getIssuedAt());
        Assertions.assertNull(responseBody.getReturnedAt());
        Assertions.assertTrue(issueRepository.findById(responseBody.getId()).isPresent());
    }

    @Test
    void testUpdateById() {
        Book book = bookService.create(new Book("Is Java the best?"));
        Reader reader = readerService.create(new Reader("Sam"));
        Issue updatedIssue = issueService.saveIssue(new Issue(book.getId(), reader.getId()));
        JUnitIssue requestForUpdate = new JUnitIssue(book.getId(), reader.getId());

        JUnitIssue responseBody = webTestClient.put()
                .uri("/issue/" + updatedIssue.getId())
                .bodyValue(requestForUpdate)
                .exchange()
                .expectStatus().isOk()
                .expectBody(JUnitIssue.class)
                .returnResult().getResponseBody();

        Assertions.assertNotNull(responseBody);
        Assertions.assertEquals(updatedIssue.getId(), responseBody.getId());
        Assertions.assertEquals(updatedIssue.getBookId(), responseBody.getBookId());
        Assertions.assertEquals(updatedIssue.getReaderId(), responseBody.getReaderId());
        Assertions.assertEquals(updatedIssue.getIssuedAt(), responseBody.getIssuedAt());
        Assertions.assertEquals(LocalDate.now(), responseBody.getReturnedAt());
        Assertions.assertTrue(issueRepository.findById(updatedIssue.getId()).isPresent());
    }

    @Test
    void testDeleteById() {
        Book book = bookService.create(new Book("What is Scala?"));
        Reader reader = readerService.create(new Reader("Jack"));
        Issue deletedIssue = issueService.saveIssue(new Issue(book.getId(), reader.getId()));

        webTestClient.delete()
                .uri("/issue/" + deletedIssue.getId())
                .exchange()
                .expectStatus().isNoContent();

        // Проверяем также, что выдачи с данным Id нет в БД.
        Assertions.assertFalse(issueRepository.findById(deletedIssue.getId()).isPresent());
    }
}