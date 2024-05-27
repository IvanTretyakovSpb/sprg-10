package ru.gb.springdemo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.gb.springdemo.model.Book;
import ru.gb.springdemo.repository.BookRepository;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public Book getByID(Long id) {
        return bookRepository.getByID(id);
    }

    public List<Book> getAll() {
        return bookRepository.getAll();
    }

    public Book create(Book book) {
        return bookRepository.save(book);
    }

    public Book update(Long id, Book book) {
        return bookRepository.update(id, book);
    }

    public void deleteById(Long id) {
        bookRepository.deleteById(id);
    }
}
