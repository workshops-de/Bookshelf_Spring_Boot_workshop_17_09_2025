package de.workshops.bookshelf.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@Import(BookJdbcRepository.class)
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BookJdbcRepositoryTest {

  @Autowired
  private BookJdbcRepository repository;

  @Test
  void findAllBooks() {
    assertThat(repository.findAllBooks()).hasSizeGreaterThanOrEqualTo(3);
  }

  @Test
  void saveBook() {
    var bookToSave = new Book();
    bookToSave.setTitle("Test");
    bookToSave.setIsbn("123456789");
    bookToSave.setAuthor("Test Author");
    bookToSave.setDescription("Test Description");
    var savedBook = repository.saveBook(bookToSave);

    assertThat(savedBook.getId()).isNotNull();
    assertThat(savedBook.getTitle()).isEqualTo("Test");
    assertThat(savedBook.getIsbn()).isEqualTo("123456789");
    assertThat(savedBook.getAuthor()).isEqualTo("Test Author");
    assertThat(savedBook.getDescription()).isEqualTo("Test Description");
  }
}