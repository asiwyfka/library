package library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.time.LocalDateTime;

/**
 * Сущность с информацией о книгах, выданных пользователям.
 *
 * @author Avdeyev Viktor
 */
@Entity
@Table(name = "loans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Loan {

    /**
     * Id записи о выдаче.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "loan_id", nullable = false, updatable = false)
    @NotNull(message = "Loan id cannot be null")
    private Long id;

    /**
     * Пользователь, который взял книгу.
     * Связь с сущностью User.
     */
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, referencedColumnName = "user_id")
    @NotNull(message = "User id cannot be null")
    private User user;

    /**
     * Книга, которая была выдана.
     * Связь с сущностью Book.
     */
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false, referencedColumnName = "book_id")
    @NotNull(message = "Book id cannot be null")
    private Book book;

    /**
     * Дата выдачи книги.
     */
    @Column(name = "loan_date", nullable = false, updatable = false)
    @NotNull(message = "Loan date cannot be null")
    private LocalDateTime loanDate = LocalDateTime.now();

    /**
     * Дата возврата книги.
     */
    @Column(name = "return_date")
    private LocalDateTime returnDate;

    /**
     * Маркер возврата книги.
     */
    @Column(name = "returned", nullable = false)
    @NotNull(message = "Returning mark cannot be null")
    private Boolean returned = false;
}
