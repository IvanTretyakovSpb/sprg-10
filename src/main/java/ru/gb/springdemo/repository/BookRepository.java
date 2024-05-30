package ru.gb.springdemo.repository;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Repository;
import ru.gb.springdemo.model.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Класс, описывающий взаимодействие с хранилищем книг, которое реализовано в виде ConcurrentHashMap
 */
@Repository
public class BookRepository {
    private static AtomicLong count = new AtomicLong(0L);
    private final Map<Long, Book> books = new ConcurrentHashMap<>();

    // После создания бина наполняем хранилище стартовым набором книг
    @PostConstruct
    private void generateBooks() {
        books.put(count.incrementAndGet(),
                new Book(count.get(), "Clean code"));
        books.put(count.incrementAndGet(),
                new Book(count.get(), "Thinking in Java"));
        books.put(count.incrementAndGet(),
                new Book(count.get(), "Spring in Action"));
        books.put(count.incrementAndGet(),
                new Book(count.get(), "Spring Starts Here"));
        books.put(count.incrementAndGet(),
                new Book(count.get(), "Spring 5 Design Patterns"));
    }

    public Book getByID(Long id) {
        return books.get(id);
    }

    public List<Book> getAll() {
        return new ArrayList<>(books.values());
    }

    public Book save(Book book) {
        book.setId(count.incrementAndGet());
        books.put(book.getId(), book);
        return book;
    }

    public Book update(Long id, Book book) {
        Book updatedBook = books.get(id);
        if (updatedBook != null) {
            updatedBook.setName(book.getName());
        }
        return updatedBook;
    }

    public void deleteById(Long id) {
        books.remove(id);
    }
}
