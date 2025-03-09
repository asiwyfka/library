package library.service.impl;

import library.entity.Loan;
import library.exception.NotFoundException;
import library.repository.LoanRepository;
import library.service.LoanService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Реализация {@link LoanService}.
 *
 * @author Avdeyev Viktor
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class LoanServiceImpl implements LoanService {

    private final LoanRepository loanRepository;

    @Override
    public List<Loan> findAll() {
        log.info("findAll - начало");
        List<Loan> loans = loanRepository.findAll();
        log.info("findAll - конец, количество займов = {}", loans.size());
        return loans;
    }

    @Override
    public Optional<Loan> findById(Long loanId) {
        log.info("findById - начало, loanId = {}", loanId);
        Optional<Loan> loan = loanRepository.findById(loanId);
        if (loan.isEmpty()) {
            log.warn("findById - займ с ID {} не найден", loanId);
            throw new NotFoundException("Займ с ID " + loanId + " не найден");
        }
        log.info("findById - конец, найден ли займ = {}", loan.isPresent());
        return loan;
    }

    @Override
    public List<Loan> findByUserId(Long userId) {
        log.info("findByUserId - начало, userId = {}", userId);
        List<Loan> loans = loanRepository.findByUserId(userId);
        if (loans.isEmpty()) {
            log.warn("findByUserId - займы для пользователя с ID {} не найдены", userId);
            throw new NotFoundException("Займы для пользователя с ID " + userId + " не найдены");
        }
        log.info("findByUserId - конец, количество займов = {}", loans.size());
        return loans;
    }

    @Override
    public List<Loan> findByReturnedFalse() {
        log.info("findByReturnedFalse - начало");
        List<Loan> loans = loanRepository.findByReturnedFalse();
        if (loans.isEmpty()) {
            log.warn("findByReturnedFalse - незавершенные займы не найдены");
            throw new NotFoundException("Незавершенные займы не найдены");
        }
        log.info("findByReturnedFalse - конец, количество незавершенных займов = {}", loans.size());
        return loans;
    }

    @Override
    public Loan save(Loan loan) {
        log.info("save - начало, заем = {}", loan);
        if (loan == null) {
            log.error("save - предоставлен пустой заем");
            throw new IllegalArgumentException("Заем не может быть пустым");
        }
        Loan savedLoan = loanRepository.save(loan);
        log.info("save - конец, сохранен займ с ID = {}", savedLoan.getId());
        return savedLoan;
    }

    @Override
    public void updateReturnStatus(Long loanId, Boolean returned) {
        log.info("updateReturnStatus - начало, loanId = {}, возвращен = {}", loanId, returned);
        Optional<Loan> loan = loanRepository.findById(loanId);
        if (loan.isEmpty()) {
            log.warn("updateReturnStatus - займ с ID {} не найден", loanId);
            throw new NotFoundException("Займ с ID " + loanId + " не найден");
        }
        loanRepository.updateReturnStatus(loanId, returned);
        log.info("updateReturnStatus - конец, loanId = {}, возвращен = {}", loanId, returned);
    }

    @Override
    public void deleteById(Long loanId) {
        log.info("deleteById - начало, loanId = {}", loanId);
        Optional<Loan> loan = loanRepository.findById(loanId);
        if (loan.isEmpty()) {
            log.warn("deleteById - займ с ID {} не найден", loanId);
            throw new NotFoundException("Займ с ID " + loanId + " не найден");
        }
        loanRepository.deleteById(loanId);
        log.info("deleteById - конец, loanId = {}", loanId);
    }
}