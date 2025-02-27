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
 *
 * @author Avdeyev Viktor
 */
@Tag(name = "Контроллер для управления пользователями")
@RestController
@Slf4j
@RequestMapping("/library/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Получить список всех пользователей")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        log.info("getAllUsers - start");
        List<User> users = userService.findAll();
        log.info("getAllUsers - end");
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Получить пользователя по ID")
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable Long userId) {
        log.info("getUserById - start, userId = {}", userId);
        Optional<User> user = userService.findById(userId);
        return user.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Получить пользователей по имени")
    @GetMapping("/first-name/{firstName}")
    public ResponseEntity<List<User>> getUsersByFirstName(@PathVariable String firstName) {
        log.info("getUsersByFirstName - start, firstName = {}", firstName);
        List<User> users = userService.findByFirstName(firstName);
        log.info("getUsersByFirstName - end");
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Получить пользователей по фамилии")
    @GetMapping("/last-name/{lastName}")
    public ResponseEntity<List<User>> getUsersByLastName(@PathVariable String lastName) {
        log.info("getUsersByLastName - start, lastName = {}", lastName);
        List<User> users = userService.findByLastName(lastName);
        log.info("getUsersByLastName - end");
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Получить пользователей, зарегистрированных после указанной даты")
    @GetMapping("/registered-after/{date}")
    public ResponseEntity<List<User>> getUsersByDateRegistrationAfter(@PathVariable LocalDate date) {
        log.info("getUsersByDateRegistrationAfter - start, date = {}", date);
        List<User> users = userService.findByDateRegistrationAfter(date);
        log.info("getUsersByDateRegistrationAfter - end");
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Добавить нового пользователя")
    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        log.info("addUser - start, user = {}", user);
        User savedUser = userService.save(user);
        log.info("addUser - end, user = {}", savedUser);
        return ResponseEntity.ok(savedUser);
    }

    @Operation(summary = "Обновить информацию о пользователе")
    @PutMapping("/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId, @RequestBody User user) {
        log.info("updateUser - start, userId = {}, user = {}", userId, user);
        user.setId(userId);
        User updatedUser = userService.update(userId, user);
        log.info("updateUser - end, user = {}", updatedUser);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Удалить пользователя по ID")
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        log.info("deleteUser - start, userId = {}", userId);
        userService.deleteById(userId);
        log.info("deleteUser - end, userId = {}", userId);
        return ResponseEntity.noContent().build();
    }
}
