package library.service.impl;

import library.entity.Book;
import library.exception.NotFoundException;
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
        log.info("Запрос на получение всех книг");
        List<Book> books = bookRepository.findAll();
        log.info("Найдено {} книг", books.size());
        return books;
    }

    @Override
    public Optional<Book> findById(Long bookId) {
        log.info("Поиск книги по ID: {}", bookId);
        return bookRepository.findById(bookId)
            .or(() -> {
                log.warn("Книга с ID {} не найдена", bookId);
                throw new NotFoundException("Книга с ID " + bookId + " не найдена");
            });
    }

    @Override
    public Optional<Book> findByTitle(String title) {
        log.info("Поиск книги по названию: {}", title);
        return bookRepository.findByTitle(title)
            .or(() -> {
                log.warn("Книга с названием '{}' не найдена", title);
                throw new NotFoundException("Книга с названием '" + title + "' не найдена");
            });
    }

    @Override
    public List<Book> findByAvailableTrue() {
        log.info("Поиск всех доступных книг");
        List<Book> books = bookRepository.findByAvailableTrue();
        log.info("Найдено {} доступных книг", books.size());
        return books;
    }

    @Override
    public List<Book> findByAuthor(String author) {
        log.info("Поиск книг автора: {}", author);
        List<Book> books = bookRepository.findByAuthor(author);
        if (books.isEmpty()) {
            log.warn("Книги автора '{}' не найдены", author);
            throw new NotFoundException("Книги автора '" + author + "' не найдены");
        }
        log.info("Найдено {} книг автора '{}'", books.size(), author);
        return books;
    }

    @Override
    public List<Book> findByPublishedYearAfter(Integer year) {
        log.info("Поиск книг, опубликованных после {}", year);
        List<Book> books = bookRepository.findByPublishedYearAfter(year);
        if (books.isEmpty()) {
            log.warn("Книги, опубликованные после {}, не найдены", year);
            throw new NotFoundException("Книги, опубликованные после " + year + ", не найдены");
        }
        log.info("Найдено {} книг, опубликованных после {}", books.size(), year);
        return books;
    }

    @Override
    public List<Book> findByPageCountGreaterThan(Integer pageCount) {
        log.info("Поиск книг с количеством страниц больше {}", pageCount);
        List<Book> books = bookRepository.findByPageCountGreaterThan(pageCount);
        if (books.isEmpty()) {
            log.warn("Книги с количеством страниц > {} не найдены", pageCount);
            throw new NotFoundException("Книги с количеством страниц > " + pageCount + " не найдены");
        }
        log.info("Найдено {} книг с более чем {} страницами", books.size(), pageCount);
        return books;
    }

    @Override
    public Book save(Book book) {
        log.info("Сохранение книги: {}", book);
        if (book == null) {
            log.error("Сохранение книги - предоставлена пустая книга");
            throw new IllegalArgumentException("Книга не может быть пустой");
        }
        Book savedBook = bookRepository.save(book);
        log.info("Книга сохранена: {}", savedBook);
        return savedBook;
    }


    @Override
    public void deleteById(Long bookId) {
        log.info("Удаление книги с ID: {}", bookId);
        if (bookRepository.findById(bookId).isEmpty()) {
            log.error("Ошибка: невозможно удалить книгу с ID {}, так как она не найдена", bookId);
            throw new NotFoundException("Книга с ID " + bookId + " не найдена");
        }
        bookRepository.deleteById(bookId);
        log.info("Книга с ID {} успешно удалена", bookId);
    }

    @Override
    public Optional<Book> update(Book book) {
        log.info("Обновление книги: {}", book);
        if (book.getId() == null || bookRepository.findById(book.getId()).isEmpty()) {
            log.error("Ошибка: невозможно обновить книгу с ID {}, так как она не найдена", book.getId());
            throw new NotFoundException("Книга с ID " + book.getId() + " не найдена");
        }
        Book updatedBook = bookRepository.update(book)
            .orElseThrow(() -> new NotFoundException("Не удалось обновить книгу с ID " + book.getId()));
        log.info("Книга обновлена: {}", updatedBook);
        return Optional.of(updatedBook);
    }
}
