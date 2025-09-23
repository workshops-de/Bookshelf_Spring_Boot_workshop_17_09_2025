package de.workshops.bookshelf.book;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
interface BookJpaRepository extends JpaRepository<Book, Long> {
  Book findByIsbn(String isbn);

  List<Book> findByAuthorLike(String author);

  @Query("SELECT b FROM Book b WHERE b.title LIKE %?1% OR b.author LIKE %?2%")
  List<Book> searchBooks(String title, String author);

  // this is analog to the query above
  // List<Book> searchBooksByTitleLikeOrAuthorLike(String title, String author);
}
