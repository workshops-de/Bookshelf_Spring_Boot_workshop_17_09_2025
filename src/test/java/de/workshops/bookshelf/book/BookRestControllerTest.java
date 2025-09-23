package de.workshops.bookshelf.book;

import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureTestDatabase
class BookRestControllerTest {
  @Autowired
  private BookRestController controller;

  @Test
  void getAllBooks() {
    // JUnit Assertion
    assertEquals(3, controller.getAllBooks().size());

    // assertJ Assertion
    assertThat(controller.getAllBooks()).hasSize(3);
  }

  @Test
  void expectConstraintViolationExceptionWhenIsbnTooLong() {
    var isbn="123456789123456789";

    assertThatThrownBy(() -> controller.getBookByIsbn(isbn))
        .isInstanceOf(ConstraintViolationException.class);
  }
}