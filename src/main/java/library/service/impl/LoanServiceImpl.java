package library.service.impl;

import library.entity.Loan;
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
        return loanRepository.findAll();
    }

    @Override
    public Optional<Loan> findById(Long loanId) {
        return loanRepository.findById(loanId);
    }

    @Override
    public List<Loan> findByUserId(Long userId) {
        return loanRepository.findByUserId(userId);
    }

    @Override
    public List<Loan> findByReturnedFalse() {
        return loanRepository.findByReturnedFalse();
    }

    @Override
    public Loan save(Loan loan) {
        log.info("Сохранение займа: {}", loan);
        return loanRepository.save(loan);
    }

    @Override
    public void updateReturnStatus(Long loanId, Boolean returned) {
        log.info("Обновление статуса возврата для займа с ID: {}", loanId);
        loanRepository.updateReturnStatus(loanId, returned);
    }

    @Override
    public void deleteById(Long loanId) {
        log.info("Удаление займа с ID: {}", loanId);
        loanRepository.deleteById(loanId);
    }
}
