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
        return entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<User> findById(Long userId) {
        return Optional.ofNullable(entityManager.find(User.class, userId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findByFirstName(String firstName) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.firstName = :firstName", User.class)
            .setParameter("firstName", firstName)
            .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findByLastName(String lastName) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.lastName = :lastName", User.class)
            .setParameter("lastName", lastName)
            .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> findByDateRegistrationAfter(LocalDate date) {
        return entityManager.createQuery("SELECT u FROM User u WHERE u.dateRegistration > :date", User.class)
            .setParameter("date", date.atStartOfDay())  // Приведение LocalDate к LocalDateTime
            .getResultList();
    }

    @Override
    @Transactional
    public User save(User user) {
        if (user.getId() == null) {
            entityManager.persist(user);
            log.info("Пользователь сохранен: {}", user);
        } else {
            user = entityManager.merge(user);
            log.info("Пользователь обновлен: {}", user);
        }
        return user;
    }

    @Override
    @Transactional
    public User update(Long userId, User updatedUser) {
        Optional<User> existingUser = findById(userId);
        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.setFirstName(updatedUser.getFirstName());
            user.setLastName(updatedUser.getLastName());
            user.setBirthDate(updatedUser.getBirthDate());
            user.setRole(updatedUser.getRole());
            user = entityManager.merge(user);
            log.info("Пользователь обновлен: {}", user);
            return user;
        }
        log.warn("Не удалось обновить пользователя: ID {} не найден", userId);
        return null;
    }

    @Override
    @Transactional
    public void deleteById(Long userId) {
        findById(userId).ifPresent(user -> {
            entityManager.remove(user);
            log.info("Пользователь удален: {}", user);
        });
    }
}
