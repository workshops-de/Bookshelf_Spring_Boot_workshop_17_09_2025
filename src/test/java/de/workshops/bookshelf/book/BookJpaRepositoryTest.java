package de.workshops.bookshelf.book;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookJpaRepositoryTest {

  @Autowired
  private BookJpaRepository repository;

  @Test
  void findAllBooks() {
    assertThat(repository.findAll()).hasSizeGreaterThanOrEqualTo(3);
  }

  @Test
  void saveBook() {
    var bookToSave = new Book();
    bookToSave.setTitle("Test");
    bookToSave.setIsbn("123456789");
    bookToSave.setAuthor("Test Author");
    bookToSave.setDescription("Test Description");
    var savedBook = repository.save(bookToSave);

    assertThat(savedBook.getId()).isNotNull();
    assertThat(savedBook.getTitle()).isEqualTo("Test");
    assertThat(savedBook.getIsbn()).isEqualTo("123456789");
    assertThat(savedBook.getAuthor()).isEqualTo("Test Author");
    assertThat(savedBook.getDescription()).isEqualTo("Test Description");
  }
}