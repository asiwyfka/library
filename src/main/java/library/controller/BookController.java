package library.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import library.entity.Book;
import library.service.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

/**
 * Контроллер для управления книгами.
 * Предоставляет API для работы с книгами: получение, добавление, обновление и удаление.
 * <p>
 * Автор: Avdeyev Viktor
 */
@Tag(name = "Контроллер для управления книгами")
@RestController
@Slf4j
@RequestMapping("/library/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    /**
     * Метод позволяет получить список всех книг в библиотеке.
     *
     * @return список всех книг
     */
    @Operation(summary = "Метод позволяет получить список всех книг")
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        log.info("getAllBooks - start");
        ResponseEntity<List<Book>> response = ResponseEntity.ok(bookService.findAll());
        log.info("getAllBooks - end, booksCount = {}", response.getBody().size());
        return response;
    }

    /**
     * Метод позволяет получить книгу по ее идентификатору.
     *
     * @param bookId идентификатор книги
     * @return найденная книга или статус 404, если книга не найдена
     */
    @Operation(summary = "Метод позволяет получить книгу по ID")
    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable Long bookId) {
        log.info("getBookById - start, bookId = {}", bookId);
        Optional<Book> book = bookService.findById(bookId);

        if (book.isEmpty()) {
            log.warn("getBookById - книга с ID {} не найдена", bookId);
            return ResponseEntity.notFound().build();
        }

        ResponseEntity<Book> response = ResponseEntity.ok(book.get());
        log.info("getBookById - end, book = {}", book.get());
        return response;
    }

    /**
     * Метод позволяет получить книгу по названию.
     *
     * @param title название книги
     * @return найденная книга или статус 404, если книга не найдена
     */
    @Operation(summary = "Метод позволяет получить книгу по названию")
    @GetMapping("/title/{title}")
    public ResponseEntity<Book> getBookByTitle(@PathVariable String title) {
        log.info("getBookByTitle - start, title = {}", title);
        Optional<Book> book = bookService.findByTitle(title);

        if (book.isEmpty()) {
            log.warn("getBookByTitle - книга с названием '{}' не найдена", title);
            return ResponseEntity.notFound().build();
        }

        ResponseEntity<Book> response = ResponseEntity.ok(book.get());
        log.info("getBookByTitle - end, book = {}", book.get());
        return response;
    }

    /**
     * Метод позволяет добавить новую книгу в библиотеку.
     *
     * @param book объект книги, который нужно добавить
     * @return добавленная книга
     */
    @Operation(summary = "Метод позволяет добавить новую книгу")
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        log.info("addBook - start, book = {}", book);
        Book savedBook = bookService.save(book);
        ResponseEntity<Book> response = ResponseEntity.ok(savedBook);
        log.info("addBook - end, savedBookId = {}", savedBook.getId());
        return response;
    }

    /**
     * Метод позволяет обновить информацию о книге.
     *
     * @param bookId идентификатор книги
     * @param book   обновленные данные книги
     * @return обновленная книга или статус 404, если книга не найдена
     */
    @Operation(summary = "Метод позволяет обновить информацию о книге")
    @PutMapping("/{bookId}")
    public ResponseEntity<Book> updateBook(@PathVariable Long bookId, @RequestBody Book book) {
        log.info("updateBook - start, bookId = {}, book = {}", bookId, book);
        book.setId(bookId);

        Optional<Book> updatedBook = bookService.update(book);
        if (updatedBook.isEmpty()) {
            log.warn("updateBook - книга с ID {} не найдена", bookId);
            return ResponseEntity.notFound().build();
        }

        ResponseEntity<Book> response = ResponseEntity.ok(updatedBook.get());
        log.info("updateBook - end, updatedBookId = {}", updatedBook.get().getId());
        return response;
    }

    /**
     * Метод позволяет удалить книгу по ее идентификатору.
     *
     * @param bookId идентификатор книги
     * @return статус 204 (No Content) или 404, если книга не найдена
     */
    @Operation(summary = "Метод позволяет удалить книгу по ID")
    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        log.info("deleteBook - start, bookId = {}", bookId);

        Optional<Book> book = bookService.findById(bookId);
        if (book.isEmpty()) {
            log.warn("deleteBook - книга с ID {} не найдена", bookId);
            return ResponseEntity.notFound().build();
        }

        bookService.deleteById(bookId);
        log.info("deleteBook - end, bookId = {}", bookId);
        return ResponseEntity.noContent().build();
    }
}
