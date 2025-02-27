package library.service;

import library.entity.Book;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с {@link Book}.
 *
 * @author Avdeyev Viktor
 */
public interface BookService {

    /**
     * Получение всех книг.
     *
     * @return список всех книг
     */
    List<Book> findAll();

    /**
     * Поиск книги по ID.
     *
     * @param bookId уникальный идентификатор книги
     * @return объект {@link Book}, если найден
     */
    Optional<Book> findById(Long bookId);

    /**
     * Поиск книги по названию.
     *
     * @param title название книги
     * @return объект {@link Book}, если найден
     */
    Optional<Book> findByTitle(String title);

    /**
     * Получение всех доступных книг.
     *
     * @return список доступных книг
     */
    List<Book> findByAvailableTrue();

    /**
     * Поиск книг по автору.
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
     * Сохранение книги.
     *
     * @param book книга для сохранения
     * @return сохраненная или обновленная книга
     */
    Book save(Book book);

    /**
     * Удаление книги по ID.
     *
     * @param bookId уникальный идентификатор книги
     */
    void deleteById(Long bookId);

    /**
     * Обновление книги.
     *
     * @param book книга с обновленными данными
     * @return обновленная книга
     */
    Book update(Book book);
}
