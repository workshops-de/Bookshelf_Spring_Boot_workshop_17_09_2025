package de.workshops.bookshelf.book;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
class BookService {
  private final BookJpaRepository bookRepository;

  BookService(BookJpaRepository bookRepository) {
    this.bookRepository = bookRepository;
  }

  List<Book> getAllBooks() {
    return bookRepository.findAll();
  }

  Book getBookByIsbn(String anIsbn) {
    var bookByIsbn = bookRepository.findByIsbn(anIsbn);
    if (bookByIsbn == null) {
      throw  new BookException("No book for this isbn found: " + anIsbn);
    }
    return bookByIsbn;
  }

  List<Book> getBooksByAuthor(String author) {
    return bookRepository.findByAuthorLike(author);
  }

  List<Book> search(BookSearchRequest searchRequest) {
    return bookRepository.searchBooks(searchRequest.title(), searchRequest.isbn());
  }

  public Book createBook(Book book) {
    return bookRepository.save(book);
  }
}
