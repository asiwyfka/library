package library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Сущность с информацией о книгах.
 *
 * @author Avdeyev Viktor
 */
@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class Book {

    /**
     * Id книги.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id", nullable = false, updatable = false)
    @NotNull(message = "Book id cannot be null")
    private Long id;

    /**
     * Название книги.
     */
    @Column(nullable = false, length = 50)
    @NotNull(message = "Book title cannot be null")
    @Size(max = 50, message = "Title cannot exceed 50 characters")
    private String title;

    /**
     * Автор книги.
     */
    @Column(length = 50)
    @Size(max = 50, message = "Author cannot exceed 50 characters")
    private String author;

    /**
     * Год издания книги.
     */
    @Column(name = "published_year")
    private Integer publishedYear;

    /**
     * Количество страниц в книге.
     */
    @Column(name = "page_count", nullable = false)
    @NotNull(message = "Page count cannot be null")
    private Integer pageCount;

    /**
     * Маркер занятости книги.
     */
    @Column(nullable = false)
    @NotNull(message = "Book available cannot be null")
    private Boolean available = true;

    /**
     * Дата добавления книги в библиотеку.
     */
    @Column(name = "added_at", nullable = false, updatable = false)
    @NotNull(message = "Adding date cannot be null")
    private LocalDateTime addedAt = LocalDateTime.now();

    /**
     * Список займов, связанных с книгой.
     * Связь с сущностью Loan.
     */
    @OneToMany(mappedBy = "book", fetch = FetchType.EAGER)
    private List<Loan> loans;
}
