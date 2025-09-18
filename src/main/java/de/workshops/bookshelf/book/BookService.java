package de.workshops.bookshelf.book;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
class BookService {
  private final BookRepository bookRepository;

  BookService(BookRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  List<Book> getAllBooks() {
    return bookRepository.findAllBooks();
  }

  Book getBookByIsbn(String anIsbn) {
    return bookRepository.findAllBooks().stream()
        .filter(book -> book.getIsbn().equals(anIsbn))
        .findFirst()
        .orElseThrow(() -> new BookException("No book for this isbn found: " + anIsbn));
  }

  List<Book> getBooksByAuthor(String author) {
    return bookRepository.findAllBooks().stream()
        .filter(book -> book.getAuthor().contains(author))
        .toList();
  }

  List<Book> search(BookSearchRequest searchRequest) {
    return bookRepository.findAllBooks().stream()
        .filter(book -> book.getTitle().contains(searchRequest.title())
            || book.getIsbn().equals(searchRequest.isbn()))
        .toList();
  }

}
