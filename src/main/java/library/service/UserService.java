package library.service;

import library.entity.User;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с {@link User}.
 *
 * @author Avdeyev Viktor
 */
public interface UserService {

    /**
     * Получение всех пользователей.
     *
     * @return список всех пользователей
     */
    List<User> findAll();

    /**
     * Поиск пользователя по ID.
     *
     * @param userId уникальный идентификатор пользователя
     * @return объект {@link User}, если найден, иначе {@link Optional#empty()}
     */
    Optional<User> findById(Long userId);

    /**
     * Поиск пользователей по имени.
     *
     * @param firstName имя пользователя
     * @return список пользователей с данным именем (пустой список, если не найдены)
     */
    List<User> findByFirstName(String firstName);

    /**
     * Поиск пользователей по фамилии.
     *
     * @param lastName фамилия пользователя
     * @return список пользователей с данной фамилией (пустой список, если не найдены)
     */
    List<User> findByLastName(String lastName);

    /**
     * Поиск пользователей, зарегистрированных после указанной даты.
     *
     * @param date дата регистрации
     * @return список пользователей, зарегистрированных после указанной даты (пустой список, если не найдены)
     */
    List<User> findByDateRegistrationAfter(LocalDate date);

    /**
     * Сохранение или обновление пользователя.
     *
     * @param user пользователь для сохранения
     * @return сохраненный или обновленный пользователь
     */
    User save(User user);

    /**
     * Обновление пользователя.
     *
     * @param userId идентификатор пользователя для обновления
     * @param updatedUser объект с обновленными данными
     * @return обновленный пользователь, если найден, иначе {@link Optional#empty()}
     */
    Optional<User> update(Long userId, User updatedUser);

    /**
     * Удаление пользователя по ID.
     *
     * @param userId уникальный идентификатор пользователя
     */
    void deleteById(Long userId);  // Изменено на void
}