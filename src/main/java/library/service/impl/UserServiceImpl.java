package library.service.impl;

import library.entity.User;
import library.repository.UserRepository;
import library.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Реализация {@link UserService}.
 *
 * @author Avdeyev Viktor
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }

    @Override
    public List<User> findByFirstName(String firstName) {
        return userRepository.findByFirstName(firstName);
    }

    @Override
    public List<User> findByLastName(String lastName) {
        return userRepository.findByLastName(lastName);
    }

    @Override
    public List<User> findByDateRegistrationAfter(LocalDate date) {
        return userRepository.findByDateRegistrationAfter(date);
    }

    @Override
    public User save(User user) {
        log.info("Сохранение пользователя: {}", user);
        return userRepository.save(user);
    }

    @Override
    public User update(Long userId, User updatedUser) {
        log.info("Обновление пользователя с ID: {}", userId);
        return userRepository.update(userId, updatedUser);
    }

    @Override
    public void deleteById(Long userId) {
        log.info("Удаление пользователя с ID: {}", userId);
        userRepository.deleteById(userId);
    }
}
