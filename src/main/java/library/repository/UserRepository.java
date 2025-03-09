package library.repository;

import library.entity.User;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с {@link User}.
 *
 * Интерфейс определяет методы для работы с сущностью пользователей.
 *
 * @author Avdeyev Viktor
 */
@Repository
public interface UserRepository {

    /**
     * Найти всех пользователей.
     *
     * @return список пользователей.
     */
    List<User> findAll();

    /**
     * Найти пользователя по ID.
     *
     * @param userId ID пользователя.
     * @return Optional с объектом User, если найден.
     */
    Optional<User> findById(Long userId);

    /**
     * Найти пользователя по имени.
     *
     * @param firstName имя пользователя.
     * @return список пользователей с данным именем.
     */
    List<User> findByFirstName(String firstName);

    /**
     * Найти пользователей по фамилии.
     *
     * @param lastName фамилия пользователя.
     * @return список пользователей с данной фамилией.
     */
    List<User> findByLastName(String lastName);

    /**
     * Найти пользователей по дате регистрации после указанной.
     *
     * @param date дата регистрации.
     * @return список пользователей.
     */
    List<User> findByDateRegistrationAfter(LocalDate date);

    /**
     * Сохранить нового пользователя или обновить существующего.
     *
     * @param user объект пользователя.
     * @return сохраненный объект пользователя.
     */
    User save(User user);

    /**
     * Обновить данные пользователя по ID.
     *
     * @param userId ID пользователя.
     * @param updatedUser объект пользователя с новыми данными.
     * @return Optional с обновленным объектом пользователя.
     */
    Optional<User> update(Long userId, User updatedUser);

    /**
     * Удалить пользователя по ID.
     *
     * @param userId ID пользователя.
     */
    void deleteById(Long userId);
}
