package library.repository.imp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import library.entity.User;
import library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Реализация {@link UserRepository} через {@link EntityManager}.
 *
 * Использует `EntityManager` для работы с базой данных.
 *
 * @author Avdeyev Viktor
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public List<User> findAll() {
        log.debug("findAll - начало");
        List<User> users = entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
        log.debug("findAll - найдено пользователей: {}", users.size());
        return users;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long userId) {
        log.debug("findById - начало, userId = {}", userId);
        User user = entityManager.find(User.class, userId);
        if (user == null) {
            log.warn("findById - пользователь с ID {} не найден", userId);
        } else {
            log.debug("findById - найден пользователь: {}", user);
        }
        return Optional.ofNullable(user); // Возвращаем Optional.empty() если не найден
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findByFirstName(String firstName) {
        log.debug("findByFirstName - начало, firstName = {}", firstName);
        List<User> users = entityManager.createQuery("SELECT u FROM User u WHERE u.firstName = :firstName", User.class)
            .setParameter("firstName", firstName)
            .getResultList();
        log.debug("findByFirstName - найдено пользователей с именем '{}': {}", firstName, users.size());
        return users;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findByLastName(String lastName) {
        log.debug("findByLastName - начало, lastName = {}", lastName);
        List<User> users = entityManager.createQuery("SELECT u FROM User u WHERE u.lastName = :lastName", User.class)
            .setParameter("lastName", lastName)
            .getResultList();
        log.debug("findByLastName - найдено пользователей с фамилией '{}': {}", lastName, users.size());
        return users;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findByDateRegistrationAfter(LocalDate date) {
        log.debug("findByDateRegistrationAfter - начало, date = {}", date);
        List<User> users = entityManager.createQuery("SELECT u FROM User u WHERE u.dateRegistration > :date", User.class)
            .setParameter("date", date.atStartOfDay())
            .getResultList();
        log.debug("findByDateRegistrationAfter - найдено пользователей, зарегистрированных после {}", date);
        return users;
    }

    @Override
    @Transactional
    public User save(User user) {
        log.debug("save - начало, user = {}", user);
        if (user.getId() == null) {
            entityManager.persist(user);
            log.info("save - пользователь сохранен: {}", user);
        } else {
            user = entityManager.merge(user);
            log.info("save - пользователь обновлен: {}", user);
        }
        return user;
    }

    @Override
    @Transactional
    public Optional<User> update(Long userId, User updatedUser) {
        log.debug("update - начало, userId = {}", userId);
        Optional<User> optionalUser = findById(userId);
        if (optionalUser.isEmpty()) {
            log.warn("update - пользователь с ID {} не найден", userId);
            return Optional.empty();
        }
        User user = optionalUser.get();
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setBirthDate(updatedUser.getBirthDate());
        user.setRole(updatedUser.getRole());
        user = entityManager.merge(user);
        log.info("update - пользователь обновлен: {}", user);
        return Optional.of(user); // Возвращаем обновленного пользователя
    }

    @Override
    @Transactional
    public void deleteById(Long userId) {
        log.debug("deleteById - начало, userId = {}", userId);
        Optional<User> optionalUser = findById(userId);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            entityManager.remove(user);
            log.info("deleteById - пользователь удален: {}", user);
        } else {
            log.warn("deleteById - пользователь с ID {} не найден", userId);
        }
    }
}