package library.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import library.entity.User;
import library.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

/**
 * Контроллер для управления пользователями.
 * Предоставляет API для работы с пользователями: получение, добавление, обновление и удаление.
 * <p>
 * Автор: Avdeyev Viktor
 */
@Tag(name = "Контроллер для управления пользователями")
@RestController
@Slf4j
@RequestMapping("/library/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * Метод позволяет получить список всех пользователей.
     *
     * @return список всех пользователей
     */
    @Operation(summary = "Метод позволяет получить список всех пользователей")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("getAllUsers - start");
        List<User> users = userService.findAll();
        ResponseEntity<List<User>> response = ResponseEntity.ok(users);
        log.info("getAllUsers - end, usersCount = {}", users.size());
        return response;
    }

    /**
     * Метод позволяет получить пользователя по его идентификатору.
     *
     * @param userId идентификатор пользователя
     * @return найденный пользователь или статус 404, если пользователь не найден
     */
    @Operation(summary = "Метод позволяет получить пользователя по ID")
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        log.info("getUserById - start, userId = {}", userId);
        Optional<User> user = userService.findById(userId);

        if (user.isEmpty()) {
            log.warn("getUserById - пользователь с ID {} не найден", userId);
            return ResponseEntity.notFound().build();
        }

        ResponseEntity<User> response = ResponseEntity.ok(user.get());
        log.info("getUserById - end, user = {}", user.get());
        return response;
    }

    /**
     * Метод позволяет получить пользователей по имени.
     *
     * @param firstName имя пользователя
     * @return список пользователей с данным именем или статус 404, если не найдены
     */
    @Operation(summary = "Метод позволяет получить пользователей по имени")
    @GetMapping("/first-name/{firstName}")
    public ResponseEntity<List<User>> getUsersByFirstName(@PathVariable String firstName) {
        log.info("getUsersByFirstName - start, firstName = {}", firstName);
        List<User> users = userService.findByFirstName(firstName);
        ResponseEntity<List<User>> response = ResponseEntity.ok(users);
        log.info("getUsersByFirstName - end, usersCount = {}", users.size());
        return response;
    }

    /**
     * Метод позволяет получить пользователей по фамилии.
     *
     * @param lastName фамилия пользователя
     * @return список пользователей с данной фамилией или статус 404, если не найдены
     */
    @Operation(summary = "Метод позволяет получить пользователей по фамилии")
    @GetMapping("/last-name/{lastName}")
    public ResponseEntity<List<User>> getUsersByLastName(@PathVariable String lastName) {
        log.info("getUsersByLastName - start, lastName = {}", lastName);
        List<User> users = userService.findByLastName(lastName);
        ResponseEntity<List<User>> response = ResponseEntity.ok(users);
        log.info("getUsersByLastName - end, usersCount = {}", users.size());
        return response;
    }

    /**
     * Метод позволяет получить пользователей, зарегистрированных после указанной даты.
     *
     * @param date дата регистрации
     * @return список пользователей, зарегистрированных после указанной даты
     */
    @Operation(summary = "Метод позволяет получить пользователей, зарегистрированных после указанной даты")
    @GetMapping("/registered-after/{date}")
    public ResponseEntity<List<User>> getUsersByDateRegistrationAfter(@PathVariable LocalDate date) {
        log.info("getUsersByDateRegistrationAfter - start, date = {}", date);
        List<User> users = userService.findByDateRegistrationAfter(date);
        ResponseEntity<List<User>> response = ResponseEntity.ok(users);
        log.info("getUsersByDateRegistrationAfter - end, usersCount = {}", users.size());
        return response;
    }

    /**
     * Метод позволяет добавить нового пользователя.
     *
     * @param user объект пользователя, который нужно добавить
     * @return добавленный пользователь
     */
    @Operation(summary = "Метод позволяет добавить нового пользователя")
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        log.info("addUser - start, user = {}", user);
        User savedUser = userService.save(user);
        ResponseEntity<User> response = ResponseEntity.ok(savedUser);
        log.info("addUser - end, savedUserId = {}", savedUser.getId());
        return response;
    }

    /**
     * Метод позволяет обновить информацию о пользователе.
     *
     * @param userId идентификатор пользователя
     * @param user   обновленные данные пользователя
     * @return обновленный пользователь или статус 404, если пользователь не найден
     */
    @Operation(summary = "Метод позволяет обновить информацию о пользователе")
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User user) {
        log.info("updateUser - start, userId = {}, user = {}", userId, user);
        user.setId(userId);

        Optional<User> updatedUser = userService.update(userId, user);
        if (updatedUser.isEmpty()) {
            log.warn("updateUser - пользователь с ID {} не найден", userId);
            return ResponseEntity.notFound().build();
        }

        ResponseEntity<User> response = ResponseEntity.ok(updatedUser.get());
        log.info("updateUser - end, updatedUserId = {}", updatedUser.get().getId());
        return response;
    }

    /**
     * Метод позволяет удалить пользователя по его идентификатору.
     *
     * @param userId идентификатор пользователя
     * @return статус 204 (No Content) или 404, если пользователь не найден
     */
    @Operation(summary = "Метод позволяет удалить пользователя по ID")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        log.info("deleteUser - start, userId = {}", userId);

        Optional<User> user = userService.findById(userId);
        if (user.isEmpty()) {
            log.warn("deleteUser - пользователь с ID {} не найден", userId);
            return ResponseEntity.notFound().build();
        }

        userService.deleteById(userId);
        log.info("deleteUser - end, userId = {}", userId);
        return ResponseEntity.noContent().build();
    }
}
