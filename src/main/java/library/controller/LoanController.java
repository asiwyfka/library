package library.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import library.entity.Loan;
import library.exception.NotFoundException;
import library.service.LoanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Контроллер для управления займами.
 * Предоставляет API для работы с займами: получение, добавление, обновление и удаление.
 * <p>
 * Автор: Avdeyev Viktor
 */
@Tag(name = "Контроллер для управления займами")
@RestController
@Slf4j
@RequestMapping("/library/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    /**
     * Метод позволяет получить список всех займов.
     *
     * @return список всех займов
     */
    @Operation(summary = "Получить список всех займов")
    @GetMapping
    public ResponseEntity<List<Loan>> getAllLoans() {
        log.info("getAllLoans - start");
        List<Loan> loans = loanService.findAll();
        log.info("getAllLoans - end, количество займов = {}", loans.size());
        return ResponseEntity.ok(loans);
    }

    /**
     * Метод позволяет получить заем по ID.
     *
     * @param loanId идентификатор займа
     * @return найденный заем или статус 404, если заем не найден
     */
    @Operation(summary = "Получить заем по ID")
    @GetMapping("/{loanId}")
    public ResponseEntity<Loan> getLoanById(@PathVariable Long loanId) {
        log.info("getLoanById - start, loanId = {}", loanId);
        Optional<Loan> loan = loanService.findById(loanId);

        if (loan.isEmpty()) {
            log.warn("getLoanById - заем с ID {} не найден", loanId);
            return ResponseEntity.notFound().build();
        }

        ResponseEntity<Loan> response = ResponseEntity.ok(loan.get());
        log.info("getLoanById - end, найден заем = {}", loan.get());
        return response;
    }

    /**
     * Метод позволяет получить список займов по ID пользователя.
     *
     * @param userId идентификатор пользователя
     * @return список займов пользователя
     */
    @Operation(summary = "Получить список займов по ID пользователя")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Loan>> getLoansByUserId(@PathVariable Long userId) {
        log.info("getLoansByUserId - start, userId = {}", userId);
        List<Loan> loans = loanService.findByUserId(userId);
        log.info("getLoansByUserId - end, количество займов = {}", loans.size());
        return ResponseEntity.ok(loans);
    }

    /**
     * Метод позволяет получить список незавершенных займов.
     *
     * @return список незавершенных займов
     */
    @Operation(summary = "Получить список незавершенных займов")
    @GetMapping("/not-returned")
    public ResponseEntity<List<Loan>> getNotReturnedLoans() {
        log.info("getNotReturnedLoans - start");
        List<Loan> loans = loanService.findByReturnedFalse();
        log.info("getNotReturnedLoans - end, количество незавершенных займов = {}", loans.size());
        return ResponseEntity.ok(loans);
    }

    /**
     * Метод позволяет добавить новый заем.
     *
     * @param loan объект займа, который нужно добавить
     * @return добавленный заем
     */
    @Operation(summary = "Добавить новый заем")
    @PostMapping
    public ResponseEntity<Loan> addLoan(@RequestBody Loan loan) {
        log.info("addLoan - start, заем = {}", loan);
        Loan savedLoan = loanService.save(loan);
        ResponseEntity<Loan> response = ResponseEntity.ok(savedLoan);
        log.info("addLoan - end, сохранен заем с ID = {}", savedLoan.getId());
        return response;
    }

    /**
     * Метод позволяет обновить статус возврата займа.
     *
     * @param loanId   идентификатор займа
     * @param returned статус возврата
     * @return статус 204 (No Content), если статус обновлен, или 404, если заем не найден
     */
    @Operation(summary = "Обновить статус возврата займа")
    @PutMapping("/{loanId}/return-status")
    public ResponseEntity<Void> updateReturnStatus(@PathVariable Long loanId, @RequestParam Boolean returned) {
        log.info("updateReturnStatus - start, loanId = {}, возвращен = {}", loanId, returned);

        try {
            loanService.updateReturnStatus(loanId, returned);
            log.info("updateReturnStatus - end");
            return ResponseEntity.noContent().build();
        } catch (NotFoundException ex) {
            log.warn("updateReturnStatus - ошибка: {}", ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Метод позволяет удалить заем по ID.
     *
     * @param loanId идентификатор займа
     * @return статус 204 (No Content) или 404, если заем не найден
     */
    @Operation(summary = "Удалить заем по ID")
    @DeleteMapping("/{loanId}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long loanId) {
        log.info("deleteLoan - start, loanId = {}", loanId);

        try {
            loanService.deleteById(loanId);  // Пытаемся удалить заем
            log.info("deleteLoan - end, заем с ID {} удален", loanId);
            return ResponseEntity.noContent().build();  // Успешное удаление
        } catch (NotFoundException ex) {
            log.warn("deleteLoan - ошибка: {}", ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }
}
