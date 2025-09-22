package de.workshops.bookshelf.book;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;

//@Configuration
public class ApplicationConfig {

  @Bean
  BookRepository bookRepository(ObjectMapper objectMapper, ResourceLoader resourceLoader) {
    return new BookRepository(objectMapper, resourceLoader);
  }

  @Bean
  BookService bookService(BookRepository bookRepository) {
    return new BookService(bookRepository);
  }
}
