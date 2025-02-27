package library.service.impl;

import library.entity.Book;
import library.repository.BookRepository;
import library.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Реализация {@link BookService}.
 *
 * @author Avdeyev Viktor
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    @Override
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long bookId) {
        return bookRepository.findById(bookId);
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    public List<Book> findByAvailableTrue() {
        return bookRepository.findByAvailableTrue();
    }

    @Override
    public List<Book> findByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    @Override
    public List<Book> findByPublishedYearAfter(Integer year) {
        return bookRepository.findByPublishedYearAfter(year);
    }

    @Override
    public List<Book> findByPageCountGreaterThan(Integer pageCount) {
        return bookRepository.findByPageCountGreaterThan(pageCount);
    }

    @Override
    public Book save(Book book) {
        log.info("Сохранение книги: {}", book);
        return bookRepository.save(book);
    }

    @Override
    public void deleteById(Long bookId) {
        log.info("Удаление книги с ID: {}", bookId);
        bookRepository.deleteById(bookId);
    }

    @Override
    public Book update(Book book) {
        log.info("Обновление книги: {}", book);
        return bookRepository.update(book);
    }
}
