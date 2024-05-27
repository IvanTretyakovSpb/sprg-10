package ru.gb.springdemo.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.gb.springdemo.model.Issue;
import ru.gb.springdemo.model.Reader;
import ru.gb.springdemo.service.ReaderService;

import java.util.List;

@RestController
@RequestMapping("/reader")
public class ReaderController {
    private final ReaderService readerService;

    @Autowired
    public ReaderController(ReaderService readerService) {
        this.readerService = readerService;
    }


    @GetMapping("/{id}")
    public ResponseEntity<Reader> getByID(@PathVariable Long id) {
        Reader reader = readerService.getByID(id);
        if (reader != null) {
            return new ResponseEntity<>(reader, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<Reader>> getAll() {
        return ResponseEntity.ok(readerService.getAll());
    }

    /**
     * Возвращает список выдач данного читателя с невозвращёнными книгами
     *
     * @param id - идентификатор читателя
     * @return - список выдач с книгами, которые на руках у читателя
     */
    @GetMapping("/{id}/issue")
    public ResponseEntity<List<Issue>> getIssuesByReaderID(@PathVariable("id") Long id) {
        Reader reader = readerService.getByID(id);
        if (reader != null) {
            return new ResponseEntity<>(readerService.getIssues(id), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Reader> create(@RequestBody Reader reader) {
        if (reader != null) {
            return new ResponseEntity<>(readerService.create(reader), HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reader> update(@PathVariable("id") Long id, @RequestBody Reader reader) {
        if (reader != null) {
            Reader updatedReader = readerService.update(id, reader);
            if (updatedReader != null) {
                return new ResponseEntity<>(readerService.update(id, reader), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        readerService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
