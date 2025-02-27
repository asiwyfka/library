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
        return entityManager.createQuery("SELECT l FROM Loan l", Loan.class).getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Loan> findById(Long loanId) {
        return Optional.ofNullable(entityManager.find(Loan.class, loanId));
    }

    @Override
    @Transactional(readOnly = true)
    public List<Loan> findByUserId(Long userId) {
        return entityManager.createQuery("SELECT l FROM Loan l WHERE l.user.id = :userId", Loan.class)
            .setParameter("userId", userId)
            .getResultList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<Loan> findByReturnedFalse() {
        return entityManager.createQuery("SELECT l FROM Loan l WHERE l.returned = false", Loan.class)
            .getResultList();
    }

    @Override
    @Transactional
    public Loan save(Loan loan) {
        if (loan.getId() == null) {
            entityManager.persist(loan);
            log.info("Займ сохранен: {}", loan);
        } else {
            loan = entityManager.merge(loan);
            log.info("Займ обновлен: {}", loan);
        }
        return loan;
    }

    @Override
    @Transactional
    public void updateReturnStatus(Long loanId, Boolean returned) {
        findById(loanId).ifPresent(loan -> {
            loan.setReturned(returned);
            if (returned) {
                loan.setReturnDate(java.time.LocalDateTime.now());
            }
            entityManager.merge(loan);
            log.info("Статус возврата обновлен для займа {}: {}", loanId, returned);
        });
    }

    @Override
    @Transactional
    public void deleteById(Long loanId) {
        findById(loanId).ifPresent(loan -> {
            entityManager.remove(loan);
            log.info("Займ удален: {}", loan);
        });
    }
}
