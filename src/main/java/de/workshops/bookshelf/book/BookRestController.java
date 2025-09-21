package de.workshops.bookshelf.book;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Size;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(BookRestController.PATH)
@Validated
class BookRestController {
  static final String PATH = "book";
  private final BookService bookService;

  BookRestController(BookService bookService) {
    this.bookService = bookService;
  }

  @GetMapping
  List<Book> getAllBooks() {
    return bookService.getAllBooks();
  }

  @GetMapping("/{isbn}")
  Book getBookByIsbn(@PathVariable(name = "isbn") @Size(min = 1, max = 14) String anIsbn) {
    return bookService.getBookByIsbn(anIsbn);
  }

  @GetMapping(params = "author")
  List<Book> getBooksByAuthor(@RequestParam @Size(min = 3) String author) {
    return bookService.getBooksByAuthor(author);
  }

  @PostMapping("/search")
  List<Book> search(@RequestBody @Valid BookSearchRequest searchRequest) {
    return bookService.search(searchRequest);
  }

  @PostMapping
  ResponseEntity<Book> addBook(@RequestBody @Valid Book book) {
    var createdBook = bookService.createBook(book);
    return new ResponseEntity<>(createdBook, HttpStatus.CREATED);
  }

  @ExceptionHandler(BookException.class)
  ResponseEntity<String> handleBookException(BookException e) {
    return ResponseEntity.status(HttpStatus.NO_CONTENT).body(e.getMessage());
  }
}
