package library.service;

import library.entity.Loan;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для работы с {@link Loan}.
 *
 * @author Avdeyev Viktor
 */
public interface LoanService {

    /**
     * Получение всех займов.
     *
     * @return список всех займов
     */
    List<Loan> findAll();

    /**
     * Поиск займа по ID.
     *
     * @param loanId уникальный идентификатор займа
     * @return объект {@link Loan}, если найден
     */
    Optional<Loan> findById(Long loanId);

    /**
     * Поиск всех займов пользователя по его ID.
     *
     * @param userId уникальный идентификатор пользователя
     * @return список всех займов пользователя
     */
    List<Loan> findByUserId(Long userId);

    /**
     * Поиск всех невозвращенных займов.
     *
     * @return список невозвращенных займов
     */
    List<Loan> findByReturnedFalse();

    /**
     * Сохранение или обновление займа.
     *
     * @param loan заем для сохранения
     * @return сохраненный или обновленный заем
     */
    Loan save(Loan loan);

    /**
     * Обновление статуса возврата для займа.
     *
     * @param loanId уникальный идентификатор займа
     * @param returned статус возврата
     */
    void updateReturnStatus(Long loanId, Boolean returned);

    /**
     * Удаление займа по ID.
     *
     * @param loanId уникальный идентификатор займа
     */
    void deleteById(Long loanId);
}
