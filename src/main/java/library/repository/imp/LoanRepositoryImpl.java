package library.repository.imp;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import library.entity.Loan;
import library.repository.LoanRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Реализация {@link LoanRepository} через {@link EntityManager}.
 *
 * Использует {@link EntityManager} для работы с базой данных.
 *
 * @author Avdeyev Viktor
 */
@Repository
@RequiredArgsConstructor
@Slf4j
public class LoanRepositoryImpl implements LoanRepository {

    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    @Transactional(readOnly = true)
    public List<Loan> findAll() {
        log.info("Получение всех записей о займах");
        List<Loan> loans = entityManager.createQuery("SELECT l FROM Loan l", Loan.class).getResultList();
        log.info("Найдено {} займов", loans.size());
        return loans;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Loan> findById(Long loanId) {
        log.info("Поиск займа с ID: {}", loanId);
        Loan loan = entityManager.find(Loan.class, loanId);
        if (loan == null) {
            log.warn("Займ с ID {} не найден", loanId);
        } else {
            log.info("Займ с ID {} найден", loanId);
        }
        return Optional.ofNullable(loan);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Loan> findByUserId(Long userId) {
        log.info("Поиск всех займов пользователя с ID: {}", userId);
        List<Loan> loans = entityManager.createQuery("SELECT l FROM Loan l WHERE l.user.id = :userId", Loan.class)
            .setParameter("userId", userId)
            .getResultList();
        log.info("Найдено {} займов для пользователя с ID: {}", loans.size(), userId);
        return loans;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Loan> findByReturnedFalse() {
        log.info("Поиск незавершенных (не возвращенных) займов");
        List<Loan> loans = entityManager.createQuery("SELECT l FROM Loan l WHERE l.returned = false", Loan.class)
            .getResultList();
        log.info("Найдено {} незавершенных займов", loans.size());
        return loans;
    }

    @Override
    @Transactional
    public Loan save(Loan loan) {
        if (loan.getId() == null) {
            log.info("Сохранение нового займа: {}", loan);
            entityManager.persist(loan);
            log.info("Займ сохранен: {}", loan);
        } else {
            log.info("Обновление займа с ID: {}", loan.getId());
            loan = entityManager.merge(loan);
            log.info("Займ обновлен: {}", loan);
        }
        return loan;
    }

    @Override
    @Transactional
    public void updateReturnStatus(Long loanId, Boolean returned) {
        log.info("Обновление статуса возврата для займа с ID: {}", loanId);
        Optional<Loan> optionalLoan = findById(loanId);
        if (optionalLoan.isPresent()) {
            Loan loan = optionalLoan.get();
            loan.setReturned(returned);
            if (returned) {
                loan.setReturnDate(java.time.LocalDateTime.now());
                log.info("Дата возврата для займа с ID {} установлена на: {}", loanId, loan.getReturnDate());
            }
            entityManager.merge(loan);
            log.info("Статус возврата обновлен для займа с ID {}: {}", loanId, returned);
        } else {
            log.warn("Займ с ID {} не найден для обновления статуса возврата", loanId);
        }
    }

    @Override
    @Transactional
    public void deleteById(Long loanId) {
        log.info("Удаление займа с ID: {}", loanId);
        Optional<Loan> optionalLoan = findById(loanId);
        if (optionalLoan.isPresent()) {
            Loan loan = optionalLoan.get();
            entityManager.remove(loan);
            log.info("Займ с ID {} удален", loanId);
        } else {
            log.warn("Займ с ID {} не найден для удаления", loanId);
        }
    }
}
