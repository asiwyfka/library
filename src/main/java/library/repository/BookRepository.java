package library.repository;

import library.entity.Book;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с {@link Book}.
 *
 * Интерфейс описывает основные операции для взаимодействия с сущностью книг.
 *
 * @author Avdeyev Viktor
 */
@Repository
public interface BookRepository {

    /**
     * Получить список всех книг.
     *
     * @return список книг
     */
    List<Book> findAll();

    /**
     * Поиск книги по id в базе.
     *
     * @param bookId уникальный идентификатор книги
     * @return объект типа {@link Book}, если найден
     */
    Optional<Book> findById(Long bookId);

    /**
     * Поиск книги по названию.
     *
     * @param title название книги
     * @return объект типа {@link Book}, если найден
     */
    Optional<Book> findByTitle(String title);

    /**
     * Поиск всех доступных книг.
     *
     * @return список доступных книг
     */
    List<Book> findByAvailableTrue();

    /**
     * Поиск всех книг по автору.
     *
     * @param author автор книги
     * @return список книг автора
     */
    List<Book> findByAuthor(String author);

    /**
     * Поиск книг, опубликованных после заданного года.
     *
     * @param year год публикации
     * @return список книг, опубликованных после указанного года
     */
    List<Book> findByPublishedYearAfter(Integer year);

    /**
     * Поиск книг с количеством страниц больше указанного.
     *
     * @param pageCount минимальное количество страниц
     * @return список книг
     */
    List<Book> findByPageCountGreaterThan(Integer pageCount);

    /**
     * Сохранение книги в базе.
     *
     * @param book книга для сохранения
     * @return сохраненный объект типа {@link Book}
     */
    Book save(Book book);

    /**
     * Удаление книги по ID.
     *
     * @param bookId уникальный идентификатор книги
     */
    void deleteById(Long bookId);

    /**
     * Обновление книги в базе.
     *
     * @param book обновленные данные книги
     * @return обновленный объект типа {@link Book}
     */
    Book update(Book book);
}
