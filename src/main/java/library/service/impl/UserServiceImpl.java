package library.service.impl;

import library.entity.User;
import library.exception.NotFoundException;
import library.repository.UserRepository;
import library.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Реализация {@link UserService}.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Cacheable(value = "users", key = "'allUsers'")
    public List<User> findAll() {
        log.info("Запрос на получение всех пользователей");
        List<User> users = userRepository.findAll();
        log.info("Найдено {} пользователей", users.size());
        return users;
    }

    @Override
    @Cacheable(value = "users", key = "#userId")
    public Optional<User> findById(Long userId) {
        log.info("Поиск пользователя по ID: {}", userId);
        return userRepository.findById(userId)
            .or(() -> {
                log.warn("Пользователь с ID {} не найден", userId);
                throw new NotFoundException("Пользователь с ID " + userId + " не найден");
            });
    }

    @Override
    @Cacheable(value = "users", key = "#firstName")
    public List<User> findByFirstName(String firstName) {
        log.info("Поиск пользователей с именем: {}", firstName);
        List<User> users = userRepository.findByFirstName(firstName);
        if (users.isEmpty()) {
            log.warn("Пользователи с именем '{}' не найдены", firstName);
            throw new NotFoundException("Пользователи с именем '" + firstName + "' не найдены");
        }
        log.info("Найдено {} пользователей с именем '{}'", users.size(), firstName);
        return users;
    }

    @Override
    @Cacheable(value = "users", key = "#lastName")
    public List<User> findByLastName(String lastName) {
        log.info("Поиск пользователей с фамилией: {}", lastName);
        List<User> users = userRepository.findByLastName(lastName);
        if (users.isEmpty()) {
            log.warn("Пользователи с фамилией '{}' не найдены", lastName);
            throw new NotFoundException("Пользователи с фамилией '" + lastName + "' не найдены");
        }
        log.info("Найдено {} пользователей с фамилией '{}'", users.size(), lastName);
        return users;
    }

    @Override
    @Cacheable(value = "users", key = "#date")
    public List<User> findByDateRegistrationAfter(LocalDate date) {
        log.info("Поиск пользователей, зарегистрированных после {}", date);
        List<User> users = userRepository.findByDateRegistrationAfter(date);
        if (users.isEmpty()) {
            log.warn("Пользователи, зарегистрированные после {}, не найдены", date);
            throw new NotFoundException("Пользователи, зарегистрированные после " + date + ", не найдены");
        }
        log.info("Найдено {} пользователей, зарегистрированных после {}", users.size(), date);
        return users;
    }

    @Override
    @CacheEvict(value = "users", key = "#user.id")
    public User save(User user) {
        log.info("Сохранение пользователя: {}", user);
        if (user == null) {
            log.error("Ошибка: предоставлен пустой пользователь");
            throw new IllegalArgumentException("Пользователь не может быть пустым");
        }
        User savedUser = userRepository.save(user);
        log.info("Пользователь сохранен: {}", savedUser);
        return savedUser;
    }

    @Override
    @CachePut(value = "users", key = "#userId")
    public Optional<User> update(Long userId, User updatedUser) {
        log.info("Обновление пользователя с ID: {}, обновленные данные: {}", userId, updatedUser);
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            log.error("Ошибка: пользователь с ID {} не найден для обновления", userId);
            throw new NotFoundException("Пользователь с ID " + userId + " не найден для обновления");
        }
        updatedUser.setId(userId);
        User updated = userRepository.save(updatedUser);
        log.info("Пользователь обновлен: {}", updated);
        return Optional.ofNullable(updated);
    }

    @Override
    @CacheEvict(value = "users", key = "#userId")
    public void deleteById(Long userId) {
        log.info("Удаление пользователя с ID: {}", userId);
        if (userRepository.findById(userId).isEmpty()) {
            log.error("Ошибка: пользователь с ID {} не найден для удаления", userId);
            throw new NotFoundException("Пользователь с ID " + userId + " не найден");
        }
        userRepository.deleteById(userId);
        log.info("Пользователь с ID {} успешно удален", userId);
    }
}
