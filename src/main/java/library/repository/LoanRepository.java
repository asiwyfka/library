package library.repository;

import library.entity.Loan;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для работы с {@link Loan}.
 *
 * Определяет методы для поиска, сохранения, обновления и удаления записей о займах.
 *
 * @author Avdeyev Viktor
 */
@Repository
public interface LoanRepository {

    /**
     * Найти все записи о займах.
     *
     * @return список займов
     */
    List<Loan> findAll();

    /**
     * Найти займ по ID.
     *
     * @param loanId ID займа
     * @return объект {@link Loan}, если найден
     */
    Optional<Loan> findById(Long loanId);

    /**
     * Найти все займы конкретного пользователя.
     *
     * @param userId ID пользователя
     * @return список займов пользователя
     */
    List<Loan> findByUserId(Long userId);

    /**
     * Найти все незавершенные (не возвращенные) займы.
     *
     * @return список незавершенных займов
     */
    List<Loan> findByReturnedFalse();

    /**
     * Сохранить или обновить займ.
     *
     * @param loan объект займа
     * @return сохраненный займ
     */
    Loan save(Loan loan);

    /**
     * Обновить статус возврата книги.
     *
     * @param loanId ID займа
     * @param returned статус возврата
     */
    void updateReturnStatus(Long loanId, Boolean returned);

    /**
     * Удалить займ по ID.
     *
     * @param loanId ID займа
     */
    void deleteById(Long loanId);
}