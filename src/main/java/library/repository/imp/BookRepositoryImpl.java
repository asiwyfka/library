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
        return entityManager.createQuery("SELECT b FROM Book b", Book.class).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> findById(Long bookId) {
        return Optional.ofNullable(entityManager.find(Book.class, bookId));
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Book> findByTitle(String title) {
        return entityManager.createQuery("SELECT b FROM Book b WHERE b.title = :title", Book.class)
            .setParameter("title", title)
            .getResultStream()
            .findFirst();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findByAvailableTrue() {
        return entityManager.createQuery("SELECT b FROM Book b WHERE b.available = true", Book.class)
            .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findByAuthor(String author) {
        return entityManager.createQuery("SELECT b FROM Book b WHERE b.author = :author", Book.class)
            .setParameter("author", author)
            .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findByPublishedYearAfter(Integer year) {
        return entityManager.createQuery("SELECT b FROM Book b WHERE b.publishedYear > :year", Book.class)
            .setParameter("year", year)
            .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Book> findByPageCountGreaterThan(Integer pageCount) {
        return entityManager.createQuery("SELECT b FROM Book b WHERE b.pageCount > :pageCount", Book.class)
            .setParameter("pageCount", pageCount)
            .getResultList();
    }

    @Override
    @Transactional
    public Book save(Book book) {
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
        findById(bookId).ifPresent(book -> {
            entityManager.remove(book);
            log.info("Книга удалена: {}", book);
        });
    }

    @Override
    @Transactional
    public Book update(Book book) {
        if (book.getId() == null || findById(book.getId()).isEmpty()) {
            throw new IllegalArgumentException("Книга с ID " + book.getId() + " не найдена");
        }
        Book updatedBook = entityManager.merge(book);
        log.info("Книга обновлена: {}", updatedBook);
        return updatedBook;
    }
}
