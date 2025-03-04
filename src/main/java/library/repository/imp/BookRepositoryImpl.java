package library.repository.imp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import library.entity.Book;
import library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Реализация {@link BookRepository}.
 *
 * Использует {@link EntityManager} для взаимодействия с базой данных.
 *
 * @author Avdeyev Viktor
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class BookRepositoryImpl implements BookRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public List<Book> findAll() {
        log.info("Запрос всех книг из базы данных");
        List<Book> books = entityManager.createQuery("SELECT b FROM Book b", Book.class).getResultList();
        log.info("Найдено {} книг", books.size());
        return books;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> findById(Long bookId) {
        log.info("Поиск книги по ID: {}", bookId);
        Book book = entityManager.find(Book.class, bookId);
        if (book != null) {
            log.info("Книга с ID {} найдена", bookId);
        } else {
            log.warn("Книга с ID {} не найдена", bookId);
        }
        return Optional.ofNullable(book);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> findByTitle(String title) {
        log.info("Поиск книги по названию: {}", title);
        Optional<Book> book = entityManager.createQuery("SELECT b FROM Book b WHERE b.title = :title", Book.class)
            .setParameter("title", title)
            .getResultStream()
            .findFirst();
        if (book.isPresent()) {
            log.info("Книга с названием '{}' найдена", title);
        } else {
            log.warn("Книга с названием '{}' не найдена", title);
        }
        return book;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findByAvailableTrue() {
        log.info("Поиск доступных книг");
        List<Book> books = entityManager.createQuery("SELECT b FROM Book b WHERE b.available = true", Book.class)
            .getResultList();
        log.info("Найдено {} доступных книг", books.size());
        return books;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findByAuthor(String author) {
        log.info("Поиск книг по автору: {}", author);
        List<Book> books = entityManager.createQuery("SELECT b FROM Book b WHERE b.author = :author", Book.class)
            .setParameter("author", author)
            .getResultList();
        log.info("Найдено {} книг автора {}", books.size(), author);
        return books;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findByPublishedYearAfter(Integer year) {
        log.info("Поиск книг, опубликованных после года: {}", year);
        List<Book> books = entityManager.createQuery("SELECT b FROM Book b WHERE b.publishedYear > :year", Book.class)
            .setParameter("year", year)
            .getResultList();
        log.info("Найдено {} книг, опубликованных после {} года", books.size(), year);
        return books;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findByPageCountGreaterThan(Integer pageCount) {
        log.info("Поиск книг с количеством страниц больше, чем: {}", pageCount);
        List<Book> books = entityManager.createQuery("SELECT b FROM Book b WHERE b.pageCount > :pageCount", Book.class)
            .setParameter("pageCount", pageCount)
            .getResultList();
        log.info("Найдено {} книг с количеством страниц больше, чем {}", books.size(), pageCount);
        return books;
    }

    @Override
    @Transactional
    public Book save(Book book) {
        log.info("Сохранение книги: {}", book);
        if (book.getId() == null) {
            entityManager.persist(book);
            log.info("Книга сохранена: {}", book);
            return book;
        } else {
            Book updatedBook = entityManager.merge(book);
            log.info("Книга обновлена: {}", updatedBook);
            return updatedBook;
        }
    }

    @Override
    @Transactional
    public void deleteById(Long bookId) {
        log.info("Удаление книги с ID: {}", bookId);
        Optional<Book> book = findById(bookId);
        if (book.isPresent()) {
            entityManager.remove(book.get());
            log.info("Книга удалена: {}", book.get());
        } else {
            log.warn("Книга с ID {} не найдена для удаления", bookId);
        }
    }

    @Override
    @Transactional
    public Optional<Book> update(Book book) {
        log.info("Обновление книги с ID: {}", book.getId());
        if (book.getId() == null || !findById(book.getId()).isPresent()) {
            log.warn("Книга с ID {} не найдена для обновления", book.getId());
            return Optional.empty();
        }
        Book updatedBook = entityManager.merge(book);
        log.info("Книга обновлена: {}", updatedBook);
        return Optional.of(updatedBook);
    }
}
