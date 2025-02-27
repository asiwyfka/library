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
 *
 * @author Avdeyev Viktor
 */
@Tag(name = "Контроллер для управления книгами")
@RestController
@Slf4j
@RequestMapping("/library/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @Operation(summary = "Получить список всех книг")
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        log.info("getAllBooks - start");
        List<Book> books = bookService.findAll();
        log.info("getAllBooks - end");
        return ResponseEntity.ok(books);
    }

    @Operation(summary = "Получить книгу по ID")
    @GetMapping("/{bookId}")
    public ResponseEntity<Book> getBookById(@PathVariable Long bookId) {
        log.info("getBookById - start, bookId = {}", bookId);
        Optional<Book> book = bookService.findById(bookId);
        return book.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Получить книгу по названию")
    @GetMapping("/title/{title}")
    public ResponseEntity<Book> getBookByTitle(@PathVariable String title) {
        log.info("getBookByTitle - start, title = {}", title);
        Optional<Book> book = bookService.findByTitle(title);
        return book.map(ResponseEntity::ok)
            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Получить список доступных книг")
    @GetMapping("/available")
    public ResponseEntity<List<Book>> getAvailableBooks() {
        log.info("getAvailableBooks - start");
        List<Book> books = bookService.findByAvailableTrue();
        log.info("getAvailableBooks - end");
        return ResponseEntity.ok(books);
    }

    @Operation(summary = "Добавить новую книгу")
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        log.info("addBook - start, book = {}", book);
        Book savedBook = bookService.save(book);
        log.info("addBook - end, book = {}", savedBook);
        return ResponseEntity.ok(savedBook);
    }

    @Operation(summary = "Обновить информацию о книге")
    @PutMapping("/{bookId}")
    public ResponseEntity<Book> updateBook(@PathVariable Long bookId, @RequestBody Book book) {
        log.info("updateBook - start, bookId = {}, book = {}", bookId, book);
        book.setId(bookId);
        Book updatedBook = bookService.update(book);
        log.info("updateBook - end, book = {}", updatedBook);
        return ResponseEntity.ok(updatedBook);
    }

    @Operation(summary = "Удалить книгу по ID")
    @DeleteMapping("/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long bookId) {
        log.info("deleteBook - start, bookId = {}", bookId);
        bookService.deleteById(bookId);
        log.info("deleteBook - end, bookId = {}", bookId);
        return ResponseEntity.noContent().build();
    }
}
