package library.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import library.enums.Role;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Сущность с информацией о пользователях.
 *
 * @author Avdeyev Viktor
 */
@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class User {

    /**
     * Id пользователя.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", nullable = false, updatable = false)
    private Long id;

    /**
     * Имя пользователя.
     */
    @Column(name = "first_name", nullable = false, length = 25)
    @NotNull(message = "Firstname cannot be null")
    @Size(max = 25, message = "Firstname cannot exceed 25 characters")
    private String firstName;

    /**
     * Фамилия пользователя.
     */
    @Column(name = "last_name", nullable = false, length = 25)
    @NotNull(message = "Lastname cannot be null")
    @Size(max = 25, message = "Lastname cannot exceed 25 characters")
    private String lastName;

    /**
     * Дата рождения пользователя.
     */
    @Column(name = "birth_date")
    private LocalDate birthDate;

    /**
     * Дата регистрации пользователя.
     */
    @Column(name = "date_registration", nullable = false, updatable = false)
    @NotNull(message = "Date registration cannot be null")
    private LocalDateTime dateRegistration = LocalDateTime.now();

    /**
     * Роль пользователя.
     * По умолчанию "READER".
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "User role cannot be null")
    private Role role = Role.READER;

    /**
     * Список займов, связанных с пользователем.
     * Связь с сущностью Loan.
     */
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER)
    private List<Loan> loans;
}
