package library.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import library.entity.Loan;
import library.service.LoanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Контроллер для управления займами.
 *
 * @author Avdeyev Viktor
 */
@Tag(name = "Контроллер для управления займами")
@RestController
@Slf4j
@RequestMapping("/library/loans")
@RequiredArgsConstructor
public class LoanController {

    private final LoanService loanService;

    @Operation(summary = "Получить список всех займов")
    @GetMapping
    public ResponseEntity<List<Loan>> getAllLoans() {
        log.info("getAllLoans - start");
        List<Loan> loans = loanService.findAll();
        log.info("getAllLoans - end");
        return ResponseEntity.ok(loans);
    }

    @Operation(summary = "Получить заем по ID")
    @GetMapping("/{loanId}")
    public ResponseEntity<Loan> getLoanById(@PathVariable Long loanId) {
        log.info("getLoanById - start, loanId = {}", loanId);
        Optional<Loan> loan = loanService.findById(loanId);
        return loan.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Получить список займов по ID пользователя")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Loan>> getLoansByUserId(@PathVariable Long userId) {
        log.info("getLoansByUserId - start, userId = {}", userId);
        List<Loan> loans = loanService.findByUserId(userId);
        log.info("getLoansByUserId - end, userId = {}", userId);
        return ResponseEntity.ok(loans);
    }

    @Operation(summary = "Получить список незавершенных займов")
    @GetMapping("/not-returned")
    public ResponseEntity<List<Loan>> getNotReturnedLoans() {
        log.info("getNotReturnedLoans - start");
        List<Loan> loans = loanService.findByReturnedFalse();
        log.info("getNotReturnedLoans - end");
        return ResponseEntity.ok(loans);
    }

    @Operation(summary = "Добавить новый заем")
    @PostMapping
    public ResponseEntity<Loan> addLoan(@RequestBody Loan loan) {
        log.info("addLoan - start, loan = {}", loan);
        Loan savedLoan = loanService.save(loan);
        log.info("addLoan - end, loan = {}", savedLoan);
        return ResponseEntity.ok(savedLoan);
    }

    @Operation(summary = "Обновить статус возврата займа")
    @PutMapping("/{loanId}/return-status")
    public ResponseEntity<Void> updateReturnStatus(@PathVariable Long loanId, @RequestParam Boolean returned) {
        log.info("updateReturnStatus - start, loanId = {}, returned = {}", loanId, returned);
        loanService.updateReturnStatus(loanId, returned);
        log.info("updateReturnStatus - end");
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Удалить заем по ID")
    @DeleteMapping("/{loanId}")
    public ResponseEntity<Void> deleteLoan(@PathVariable Long loanId) {
        log.info("deleteLoan - start, loanId = {}", loanId);
        loanService.deleteById(loanId);
        log.info("deleteLoan - end, loanId = {}", loanId);
        return ResponseEntity.noContent().build();
    }
}
